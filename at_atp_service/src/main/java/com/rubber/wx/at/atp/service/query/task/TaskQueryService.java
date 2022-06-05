package com.rubber.wx.at.atp.service.query.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.dal.ITaskInfoDal;
import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luffyu
 * Created on 2022/6/1
 */
@Slf4j
@Service
public class TaskQueryService {

    @Autowired
    private ITaskInfoDal iTaskInfoDal;


    /**
     * 获取有效的任务
     */
    public TaskInfo getValidTask(TaskTypeEnums taskTypeEnums){
        QueryWrapper<TaskInfo> qw = new QueryWrapper<>();
        qw.lambda().eq(TaskInfo::getTaskType,taskTypeEnums.toString())
                .eq(TaskInfo::getStatus,1)
                .orderByDesc(TaskInfo::getId)
                .last(" limit 1");
        return iTaskInfoDal.getOne(qw);
    }

}
