package com.rubber.wx.at.atp.service.admin.match;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.dal.*;
import com.rubber.wx.at.atp.dao.entity.*;
import com.rubber.wx.at.atp.manager.reptile.PlayerGameInfoReptile;
import com.rubber.wx.at.atp.manager.reptile.model.MatchPointInfo;
import com.rubber.wx.at.atp.manager.reptile.model.PlayerGameInfoModel;
import com.rubber.wx.at.atp.service.admin.task.TaskInfoService;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import com.rubber.wx.at.atp.service.dto.response.PageRankResponse;
import com.rubber.wx.at.atp.service.query.atp.AtpRankQueryService;
import com.rubber.wx.at.atp.service.query.wta.WtaRankQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2022/5/28
 */
@Slf4j
@Service
public class PlayerMatchInfoDataSourceService {


    @Autowired
    private IWtaRankInfoDal iWtaRankInfoDal;

    @Autowired
    private IAtpPlayerInfoDal iAtpPlayerInfoDal;


    @Autowired
    private IWtaPlayerInfoDal iWtaPlayerInfoDal;


    @Autowired
    private IPlayerMatchInfoDal iPlayerMatchInfoDal;


    @Autowired
    private TaskInfoService taskInfoService;


    @Autowired
    private AtpRankQueryService atpRankQueryService;

    @Autowired
    private WtaRankQueryService wtaRankQueryService;


    public void doSaveAtpMatchInfoByTask(){
        int taskId = taskInfoService.initTaskInfo(TaskTypeEnums.ATP_MATCH_INFO);
        int page =1;
        int size = 10;
        boolean doSave = true;
        while (doSave) {
            SearchQueryDto playerPage =new SearchQueryDto();
            playerPage.setPage(page);
            playerPage.setSize(size);
            atpRankQueryService.queryByPage(playerPage);
            PageRankResponse<AtpRankInfoEntity> rankPage = atpRankQueryService.queryByPage(playerPage);
            List<AtpRankInfoEntity> records = rankPage.getRecords();

            if (CollUtil.isEmpty(records)){
                doSave = false;
                continue;
            }
            log.info("开始查询atp的球员信息，{}-{}",playerPage.getPage(),playerPage.getSize());
            for(AtpRankInfoEntity atpRank:records){
                try {
                    AtpPlayerInfoEntity atpPlayerInfoEntity = iAtpPlayerInfoDal.getByPlayerId(atpRank.getThirdId());
                    if (atpPlayerInfoEntity == null){
                        log.info("查询wta的球员{}信息不存在",atpRank.getPlayerId());
                        continue;
                    }
                    boolean result = playerMatchReptileSaveDataForAtp(atpPlayerInfoEntity);
                    log.info("查询atp的球员{}信息 结果={}",atpPlayerInfoEntity.getPlayerId(),result);
                }catch (Exception e){
                    log.info("查询atp的球员{}信息异常，msg={}",atpRank.getPlayerId(),e.getMessage());

                }
            }
            page++;
        }
        taskInfoService.finishTaskInfo(taskId);
    }


    public void doSaveWtaMatchInfoByTask(){
        int taskId = taskInfoService.initTaskInfo(TaskTypeEnums.WTA_MATCH_INFO);
        int page =1;
        int size = 10;
        boolean doSave = true;
        while (doSave) {
            SearchQueryDto playerPage =new SearchQueryDto();
            playerPage.setPage(page);
            playerPage.setSize(size);
            PageRankResponse<WtaRankInfoEntity> pageResult = wtaRankQueryService.queryByPage(playerPage);
            List<WtaRankInfoEntity> records = pageResult.getRecords();
            log.info("开始查询wta的球员信息，{}-{}",playerPage.getSize(),playerPage.getSize());
            if (CollUtil.isEmpty(records)){
                doSave = false;
                continue;
            }
            for(WtaRankInfoEntity rankInfoEntity:records){
                try {
                    WtaPlayerInfoEntity atpPlayerInfoEntity = iWtaPlayerInfoDal.getByPlayerId(rankInfoEntity.getThirdId());
                    if (atpPlayerInfoEntity == null){
                        log.info("查询wta的球员{}信息不存在",rankInfoEntity.getPlayerId());
                        continue;
                    }
                    boolean result = playerMatchReptileSaveDataForWta(atpPlayerInfoEntity);
                    log.info("查询wta的球员{}信息 结果={}",atpPlayerInfoEntity.getPlayerId(),result);
                }catch (Exception e){
                    log.info("查询wta的球员{}信息异常，msg={}",rankInfoEntity.getPlayerId(),e.getMessage());

                }
            }
            page++;
        }
        taskInfoService.finishTaskInfo(taskId);

    }






    public boolean playerMatchReptileSaveDataForAtp(AtpPlayerInfoEntity atpPlayerInfo){
        PlayerGameInfoModel playerGameInfoModel = PlayerGameInfoReptile.queryAndResolve(atpPlayerInfo.getPlayerId());
        if (playerGameInfoModel == null){
            return false;
        }
        atpPlayerInfo.setPlayerAvatar(playerGameInfoModel.getUserAvatar());
        iAtpPlayerInfoDal.updateById(atpPlayerInfo);
        // 对象转换
        List<MatchPointInfo> matchPointInfoList = playerGameInfoModel.getMatchPointInfoList();
        doHandlerSaveMatchInfoList(matchPointInfoList,atpPlayerInfo.getPlayerId());
        return true;
    }

    public boolean playerMatchReptileSaveDataForWta(WtaPlayerInfoEntity atpPlayerInfo){
        PlayerGameInfoModel playerGameInfoModel = PlayerGameInfoReptile.queryAndResolveForWta(atpPlayerInfo.getPlayerId());
        if (playerGameInfoModel == null){
            return false;
        }
        atpPlayerInfo.setPlayerAvatar(playerGameInfoModel.getUserAvatar());
        iWtaPlayerInfoDal.updateById(atpPlayerInfo);
        // 对象转换
        List<MatchPointInfo> matchPointInfoList = playerGameInfoModel.getMatchPointInfoList();
        doHandlerSaveMatchInfoList(matchPointInfoList,atpPlayerInfo.getPlayerId());
        return true;
    }



    private boolean doHandlerSaveMatchInfoList(List<MatchPointInfo> matchPointInfoList,String playerId){
        // 对象转换
        List<PlayerMatchInfo> needSaveMatchInfo = convertBean(matchPointInfoList);
        // 存取
        List<PlayerMatchInfo> playerMatchList = iPlayerMatchInfoDal.queryAllMatchByPlayerId(playerId);
        if (CollUtil.isEmpty(playerMatchList)){
            log.info("开始保存用户的比赛信息");
            needSaveMatchInfo.forEach( i-> initAdd(i,playerId));
            iPlayerMatchInfoDal.saveBatch(needSaveMatchInfo);
        }
        return true;
    }

    /**
     * 初始化
     */
    private void initAdd(PlayerMatchInfo playerMatchInfo,String playerId){
        playerMatchInfo.setPlayerId(playerId);
        playerMatchInfo.setStatus(1);
        playerMatchInfo.setVersion(1);
        playerMatchInfo.setCreateTime(LocalDateTime.now());
        playerMatchInfo.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 对象转换
     */
    private List<PlayerMatchInfo> convertBean(List<MatchPointInfo> matchPointInfoList){
        List<PlayerMatchInfo> result = new ArrayList<>();
        for (MatchPointInfo matchPointInfo:matchPointInfoList){
            List<MatchPointInfo.MatchPointBean> matchPointList = matchPointInfo.getMatchPointList();

            List<PlayerMatchInfo> single = matchPointList.stream().map(i->{
                PlayerMatchInfo matchInfo = new PlayerMatchInfo();
                BeanUtils.copyProperties(i,matchInfo);
                matchInfo.setMatchType(matchPointInfo.getMatchTypeName());
                return matchInfo;
            }).collect(Collectors.toList());
            result.addAll(single);
        }
        return result;
    }


}
