package com.rubber.wx.at.atp.admin.task;

import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.wx.at.atp.service.admin.atp.AtpPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.atp.AtpRankDataSourceService;
import com.rubber.wx.at.atp.service.admin.match.PlayerMatchInfoDataSourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaRankDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author luffyu
 * Created on 2022/5/28
 */
@Slf4j
@EnableScheduling
@Configuration
@Component
public class AutoTaskService {

    @Autowired
    private AtpPlayerDatasourceService atpPlayerDatasourceService;

    @Autowired
    private AtpRankDataSourceService atpRankDataSourceService;

    @Autowired
    private PlayerMatchInfoDataSourceService playerMatchInfoDataSourceService;

    @Autowired
    private WtaPlayerDatasourceService wtaPlayerDatasourceService;

    @Autowired
    private WtaRankDataSourceService wtaRankDataSourceService;


    /**
     * 每月定时任务
     */
    public void taskAtpInfo()  {
        atpPlayerDatasourceService.startAtpPlayerInfoReptileTask();
    }



    /**
     * 每天6点自动刷新
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void taskAtpRank(){
        atpRankDataSourceService.atpStartRankReptileTask();
    }

    /**
     * 每周定时任务
     */
    public void taskAtpMatch(){
        playerMatchInfoDataSourceService.doSaveAtpMatchInfoByTask();
    }


    /**
     * 每月定时任务
     */
    public ResultMsg taskWtaInfo(){
        wtaPlayerDatasourceService.startWatPlayerInfoReptileTask();
        return ResultMsg.success();
    }

    /**
     * 每天7点自动刷新
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void taskWtaRank(){
        wtaRankDataSourceService.wtaStartRankReptileTask();
    }

    /**
     * 每周定时任务
     */
    public void taskWtaMatch(){
        wtaPlayerDatasourceService.startWatPlayerInfoReptileTask();
    }
}
