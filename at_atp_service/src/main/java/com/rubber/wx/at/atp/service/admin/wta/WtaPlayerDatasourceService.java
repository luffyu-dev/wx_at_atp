package com.rubber.wx.at.atp.service.admin.wta;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.rubber.wx.at.atp.dao.dal.IAtpPlayerInfoDal;
import com.rubber.wx.at.atp.dao.dal.IWtaPlayerInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.entity.WtaPlayerInfoEntity;
import com.rubber.wx.at.atp.manager.model.ReptileModel;
import com.rubber.wx.at.atp.manager.reptile.AtpPlayerReptile;
import com.rubber.wx.at.atp.manager.reptile.WtaPlayerReptile;
import com.rubber.wx.at.atp.service.admin.task.TaskInfoService;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import com.rubber.wx.at.atp.service.utils.NumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/5/14
 */
@Slf4j
@Service
public class WtaPlayerDatasourceService {

    @Autowired
    private IWtaPlayerInfoDal iWtaPlayerInfoDal;


    /**
     * 一次处理的数据长度
     */
    private static final Integer HANDLER_DATA_SIZE = 100;

    @Autowired
    private TaskInfoService taskInfoService;


    public void startWatPlayerInfoReptileTask() {
        int taskId = taskInfoService.initTaskInfo(TaskTypeEnums.WTA_INFO);
        int i=0;
        while (!watPlayerInfoReptileSaveData(i*HANDLER_DATA_SIZE,HANDLER_DATA_SIZE)){
            i++;
        }
        taskInfoService.finishTaskInfo(taskId);
    }


    /**
     * 操作球员信息到数据库
     * @param index 当前的index对象
     * @param size 当时的长度
     */
    public boolean watPlayerInfoReptileSaveData(int index,int size){
        ReptileModel model = new ReptileModel();
        model.setIndex(index);
        model.setSize(size);
        log.info("开始查询数据-{}-{}",model.getIndex(),model.getSize());
        WtaPlayerReptile.queryPageWtaPlayer(model);
        if (!model.isRequestSuccess()){
            log.info("查询数据失败-{}-{}",model.getIndex(),model.getSize());
            return false;
        }
        log.info("开始查询数据-{}-{}，总数-{}",model.getIndex(),model.getSize(),model.getTotal());
        if (model.getTotal() < index  + model.getSize()){
            return true;
        }
        List<WtaPlayerInfoEntity> needAddList = new ArrayList<>();
        List<WtaPlayerInfoEntity> needUpdateList = new ArrayList<>();

        for (Object o:model.getData()){
            WtaPlayerInfoEntity  playerInfo = doConvertEntity((JSONObject)o);
            if (playerInfo == null){
                continue;
            }
            WtaPlayerInfoEntity oldPlayerInfo = iWtaPlayerInfoDal.getByPlayerId(playerInfo.getPlayerId());
            if (oldPlayerInfo == null){
                initNewPlayer(playerInfo);
                needAddList.add(playerInfo);
            }else if (checkDiff(playerInfo,oldPlayerInfo)){
                oldPlayerInfo.setDoubleRankHeight(playerInfo.getDoubleRankHeight());
                needUpdateList.add(oldPlayerInfo);
            }
        }
        if (CollUtil.isNotEmpty(needAddList)){
            iWtaPlayerInfoDal.saveBatch(needAddList);
        }
        if (CollUtil.isNotEmpty(needUpdateList)){
            iWtaPlayerInfoDal.updateBatchById(needUpdateList);
        }
        return false;
    }



    private void initNewPlayer(WtaPlayerInfoEntity data){
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        data.setVersion(0);
        data.setStatus(1);
    }


    private boolean checkDiff(WtaPlayerInfoEntity newData,WtaPlayerInfoEntity oldData){
        boolean diff = false;

        if (!oldData.getSinglesChampionNum().equals(newData.getSinglesChampionNum())){
            oldData.setSinglesChampionNum(newData.getSinglesChampionNum());
            diff = true;
        }
        if (!oldData.getSinglesRankHeight().equals(newData.getSinglesRankHeight())){
            oldData.setSinglesRankHeight(newData.getSinglesRankHeight());
            diff = true;
        }
        if (!oldData.getDoubleChampionNum().equals(newData.getDoubleChampionNum())){
            oldData.setDoubleChampionNum(newData.getDoubleChampionNum());
            diff = true;
        }
        if (!oldData.getDoubleRankHeight().equals(newData.getDoubleRankHeight())){
            oldData.setDoubleRankHeight(newData.getDoubleRankHeight());
            diff = true;
        }

        if (!oldData.getAllBonus().equals(newData.getAllBonus())){
            oldData.setAllBonus(newData.getAllBonus());
            diff = true;
        }
        if (diff){
            oldData.setCreateTime(LocalDateTime.now());
        }
        return diff;
    }


    /**
     * 数据对象的转换
     * @param object
     * @return
     */
    private WtaPlayerInfoEntity doConvertEntity(JSONObject object){
        try {
            WtaPlayerInfoEntity entity = new WtaPlayerInfoEntity();
            entity.setThirdId(object.getString("id"));
            entity.setPlayerId(entity.getThirdId());
            // name: "<img class=cImgPlayerFlag data-original=\"https://www.rank-tennis.com/images/flag_svg/SRB.svg\" />诺瓦克·德约科维奇"
            String name = object.getString("name");

            String chinaFullName = name;
            if (chinaFullName.contains("/>")){
                chinaFullName = name.substring(name.indexOf("/>") + 2);
            }
            String chinaName = chinaFullName;
            if (chinaName.contains("·")) {
                chinaName = chinaFullName.substring(chinaFullName.indexOf("·") + 1);
            }
            if (name.contains("http")){
                String nationImg = name.substring(name.indexOf("http"), name.lastIndexOf("\""));
                entity.setNationImg(nationImg);
            }
            entity.setChinaName(chinaName);
            entity.setChinaFullName(chinaFullName);
            entity.setFirstName(object.getString("first"));
            entity.setLastName(object.getString("last"));
            entity.setNationChineseName(object.getString("nationfull"));
            entity.setNationEnglishName("");
            // nationImage
            entity.setBirthday(object.getString("birthday"));
            entity.setBirthPlace(object.getString("birthplace"));
            entity.setResidence(object.getString("residence"));
            entity.setHeight(object.getString("height"));
            entity.setWeight(object.getString("weight"));
            entity.setProYear(object.getString("proyear"));

            entity.setAllBonus(object.getString("prize_c"));
            entity.setSinglesChampionNum(NumUtils.changeToNum(object.getString("title_s_c")));
            entity.setSinglesRankHeight(NumUtils.changeToNum(object.getString("rank_s_hi")));

            entity.setDoubleChampionNum(NumUtils.changeToNum(object.getString("title_d_c")));
            entity.setDoubleRankHeight(NumUtils.changeToNum(object.getString("rank_d_hi")));
            entity.setWebsite(object.getString("website"));
            return entity;
        }catch (Exception e){
            log.error("当前的数据对象转换失败，e={}",e.getMessage());
            return null;
        }
    }





}
