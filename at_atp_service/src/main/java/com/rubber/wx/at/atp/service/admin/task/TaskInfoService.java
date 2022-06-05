package com.rubber.wx.at.atp.service.admin.task;

import com.rubber.wx.at.atp.dao.dal.ITaskInfoDal;
import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author luffyu
 * Created on 2022/5/28
 */
@Service
public class TaskInfoService {

    @Autowired
    private ITaskInfoDal iTaskInfoDal;



    public Integer initTaskInfo(TaskTypeEnums taskTypeEnums,String dataVersion){
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskType(taskTypeEnums.toString());
        taskInfo.setStatus(0);
        taskInfo.setVersion(1);
        taskInfo.setCreateTime(LocalDateTime.now());
        taskInfo.setUpdateTime(LocalDateTime.now());
        taskInfo.setDataVersion(dataVersion);
        iTaskInfoDal.save(taskInfo);
        return taskInfo.getId();
    }


    public Integer initTaskInfo(TaskTypeEnums taskTypeEnums){
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskType(taskTypeEnums.toString());
        taskInfo.setStatus(0);
        taskInfo.setVersion(1);
        taskInfo.setCreateTime(LocalDateTime.now());
        taskInfo.setUpdateTime(LocalDateTime.now());
        iTaskInfoDal.save(taskInfo);
        return taskInfo.getId();
    }


    /**
     * 任务完成
     * @param id 当前的id
     */
    public void finishTaskInfo(Integer id){
        TaskInfo taskInfo = iTaskInfoDal.getById(id);
        taskInfo.setStatus(1);
        taskInfo.setUpdateTime(LocalDateTime.now());
        iTaskInfoDal.updateById(taskInfo);
    }
}
