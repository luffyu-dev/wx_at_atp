package com.rubber.wx.at.atp.service.admin.atp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.manager.model.ReptileModel;
import com.rubber.wx.at.atp.manager.reptile.AtpRankReptile;
import com.rubber.wx.at.atp.service.admin.task.TaskInfoService;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import com.rubber.wx.at.atp.service.utils.NumUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/5/20
 */
@Slf4j
@Service
public class AtpRankDataSourceService {

    @Autowired
    private IAtpRankInfoDal iAtpRankInfoDal;

    /**
     * 一次处理的数据长度
     */
    private static final Integer HANDLER_DATA_SIZE = 100;

    private static final String DATE_VERSION_FORMAT = "yyyyMMdd";

    @Autowired
    private TaskInfoService taskInfoService;

    /**
     * 任务循环执行
     */
    public void atpStartRankReptileTask(){
        LocalDateTime now = LocalDateTime.now();
        String format = DateUtil.format(now, DATE_VERSION_FORMAT);
        int taskId = taskInfoService.initTaskInfo(TaskTypeEnums.ATP_RANK,format);
        int i=0;
        while (!atpRankReptileSaveData(now, i * HANDLER_DATA_SIZE, HANDLER_DATA_SIZE)) {
            i++;
        }
        taskInfoService.finishTaskInfo(taskId);

    }

    /**
     * 操作排名信息到数据库
     * @param index 当前的index对象
     * @param size 当时的长度
     */
    public boolean atpRankReptileSaveData(LocalDateTime dateTime,int index,int size){
        log.info("开始查询排名数据-index:{}-size:{}",index,size);
        ReptileModel model = new ReptileModel();
        model.setIndex(index);
        model.setSize(size);
        AtpRankReptile.pageAtpRank(model);
        if (!model.isRequestSuccess()){
            log.error("查询数据失败-{}-{}",model.getIndex(),model.getSize());
            return false;
        }
        log.info("开始查询数据-{}-{}，总数-{}",model.getIndex(),model.getSize(),model.getTotal());
        if (model.getTotal() <  index + size){
            return true;
        }
        List<AtpRankInfoEntity> rankAddList = new ArrayList<>();
        List<AtpRankInfoEntity> rankUpdateList = new ArrayList<>();
        for (Object o:model.getData()){
            AtpRankInfoEntity rankInfoEntity = doConvertEntity((JSONObject)o);
            initNewPlayer(dateTime,rankInfoEntity);
            AtpRankInfoEntity toDayOldRanInfo = iAtpRankInfoDal.getByPlayerId(rankInfoEntity.getPlayerId(),rankInfoEntity.getDateVersion());
            if (toDayOldRanInfo == null){
                rankAddList.add(rankInfoEntity);
            }else {
                BeanUtils.copyProperties(rankInfoEntity,toDayOldRanInfo,"id","createTime","version");
                rankUpdateList.add(toDayOldRanInfo);
            }
        }
        log.info("开始写入排名数据到数据库-index:{}-size:{}",index,size);
        if (CollUtil.isNotEmpty(rankAddList)){
            iAtpRankInfoDal.saveBatch(rankAddList);
        }
        if (CollUtil.isNotEmpty(rankUpdateList)){
            iAtpRankInfoDal.updateBatchById(rankUpdateList);
        }
        return false;
    }


    /**
     * 新增初始化
     */
    private void initNewPlayer(LocalDateTime dateTime,AtpRankInfoEntity data){
        data.setDateVersion(DateUtil.format(dateTime,DATE_VERSION_FORMAT));
        data.setCreateTime(dateTime);
        data.setUpdateTime(dateTime);
        data.setVersion(0);
        data.setStatus(1);
    }


    /**
     * 数据转换
     * @param object 当前的参数
     * @return 返回数据对象
     */
    private AtpRankInfoEntity doConvertEntity(JSONObject object){
        AtpRankInfoEntity rankInfoEntity = new AtpRankInfoEntity();
        rankInfoEntity.setPlayerId(object.getString("id"));
        rankInfoEntity.setThirdId(object.getString("id"));

        String fullName = object.getString("full_name");
        String chinaFullName = fullName;
        if (chinaFullName.contains("/>")){
            chinaFullName = fullName.substring(fullName.indexOf("/>") + 2);
        }
        if (fullName.contains("http")){
            String nationImg = fullName.substring(fullName.indexOf("http"), fullName.lastIndexOf("\""));
            rankInfoEntity.setNationImg(nationImg);
        }
        rankInfoEntity.setChinaFullName(chinaFullName);
        rankInfoEntity.setNationChineseName(object.getString("nation"));

        //积分
        rankInfoEntity.setPoint(NumUtils.changeToNum(object.getString("point")));
        rankInfoEntity.setFlopPoint(NumUtils.changeToNum(object.getString("flop")));
        rankInfoEntity.setWinPoint(NumUtils.changeToNum(object.getString("w_point")));

        //排名
        rankInfoEntity.setRankChange(NumUtils.changeToNum(object.getString("change")));
        rankInfoEntity.setRank(NumUtils.changeToNum(object.getString("c_rank")));
        rankInfoEntity.setOfficialRank(NumUtils.changeToNum(object.getString("f_rank")));
        rankInfoEntity.setHighestRank(NumUtils.changeToNum(object.getString("highest")));

        //周期
        rankInfoEntity.setCycleBonus(object.getString("prize"));
        rankInfoEntity.setCycleLoseNum(NumUtils.changeToNum(object.getString("lose")));
        rankInfoEntity.setCycleWinNum(NumUtils.changeToNum(object.getString("win")));
        rankInfoEntity.setCycleWinPro(object.getString("win_r"));

        return rankInfoEntity;
    }




}
