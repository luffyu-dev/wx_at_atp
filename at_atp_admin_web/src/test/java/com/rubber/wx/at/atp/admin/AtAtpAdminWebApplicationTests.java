package com.rubber.wx.at.atp.admin;

import com.rubber.wx.at.atp.service.admin.atp.AtpPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.match.PlayerMatchInfoDataSourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaPlayerDatasourceService;
import com.rubber.wx.at.atp.service.admin.atp.AtpRankDataSourceService;
import com.rubber.wx.at.atp.service.admin.wta.WtaRankDataSourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtAtpAdminWebApplicationTests {

    @Autowired
    private AtpPlayerDatasourceService playerDatasourceService;

    @Autowired
    private AtpRankDataSourceService rankDataSourceService;

    @Autowired
    private WtaPlayerDatasourceService wtaPlayerDatasourceService;

    @Autowired
    private WtaRankDataSourceService wtaRankDataSourceService;

    @Autowired
    private PlayerMatchInfoDataSourceService playerMatchInfoDataSourceService;

    @Test
    void contextLoads() throws InterruptedException {
        //playerDatasourceService.startAtpPlayerInfoReptileTask();

        //rankDataSourceService.startRankReptileTask();

        //wtaPlayerDatasourceService.startWatPlayerInfoReptileTask();


        //wtaRankDataSourceService.wtaStartRankReptileTask();

        playerMatchInfoDataSourceService.doSaveAtpMatchInfoByTask();

    }

}
