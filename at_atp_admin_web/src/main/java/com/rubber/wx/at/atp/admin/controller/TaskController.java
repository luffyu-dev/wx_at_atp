package com.rubber.wx.at.atp.admin.controller;

import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.wx.at.atp.service.admin.atp.AtpPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.atp.AtpRankDataSourceService;
import com.rubber.wx.at.atp.service.admin.match.PlayerMatchInfoDataSourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaRankDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luffyu
 * Created on 2022/5/28
 */
@RequestMapping("/task")
@RestController
public class TaskController {

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


    private static final ThreadPoolExecutor ex = new ThreadPoolExecutor(1,2,2L, TimeUnit.HOURS,new ArrayBlockingQueue<>(100));


    @PostMapping("/atp/info")
    public ResultMsg taskAtpInfo()  {
        ex.execute(()-> {
            atpPlayerDatasourceService.startAtpPlayerInfoReptileTask();
        });
        return ResultMsg.success();
    }

    @PostMapping("/atp/rank")
    public ResultMsg taskAtpRank(){
        ex.execute(()-> {
            atpRankDataSourceService.atpStartRankReptileTask();
        });
        return ResultMsg.success();
    }


    @PostMapping("/atp/match")
    public ResultMsg taskAtpMatch(){
        ex.execute(()-> {
            playerMatchInfoDataSourceService.doSaveAtpMatchInfoByTask();
        });
        return ResultMsg.success();
    }



    @PostMapping("/wta/info")
    public ResultMsg taskWtaInfo(){
        ex.execute(()-> {
            wtaPlayerDatasourceService.startWatPlayerInfoReptileTask();
        });
        return ResultMsg.success();
    }

    @PostMapping("/wta/rank")
    public ResultMsg taskWtaRank(){
        ex.execute(()-> {
            wtaRankDataSourceService.wtaStartRankReptileTask();
        });
        return ResultMsg.success();
    }


    @PostMapping("/wta/match")
    public ResultMsg taskWtaMatch(){
        ex.execute(()-> {
            playerMatchInfoDataSourceService.doSaveWtaMatchInfoByTask();
        });
        return ResultMsg.success();
    }









}
