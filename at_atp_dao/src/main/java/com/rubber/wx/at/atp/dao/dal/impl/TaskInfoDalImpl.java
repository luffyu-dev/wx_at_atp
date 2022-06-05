package com.rubber.wx.at.atp.dao.dal.impl;

import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import com.rubber.wx.at.atp.dao.mapper.TaskInfoMapper;
import com.rubber.wx.at.atp.dao.dal.ITaskInfoDal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务记录表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-28
 */
@Service
public class TaskInfoDalImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoDal {

}
