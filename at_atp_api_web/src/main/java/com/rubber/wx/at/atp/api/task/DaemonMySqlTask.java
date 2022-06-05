package com.rubber.wx.at.atp.api.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2022/5/24
 */
@Slf4j
@EnableScheduling
@Configuration
@Component
public class DaemonMySqlTask {

    @Autowired
    private IAtpRankInfoDal iAtpRankInfoDal;

    /**
     * 用于维护数据库的连接
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void doDaemonMySql(){
        try {
            log.info("start com.rubber.wx.at.atp.api.task.DaemonMySqlTask.doDaemonMySql");
            QueryWrapper<AtpRankInfoEntity> qw = new QueryWrapper<>();
            qw.lambda().eq(AtpRankInfoEntity::getId, 1);
            iAtpRankInfoDal.list(qw);
        }catch (Exception e){
            log.error("start com.rubber.wx.at.atp.api.task.DaemonMySqlTask.doDaemonMySql msg={}",e.getMessage());
        }

    }
}
