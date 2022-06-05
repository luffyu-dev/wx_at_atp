package com.rubber.wx.at.atp.service.dto.response;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import com.rubber.wx.at.atp.service.dto.PageDto;
import lombok.Data;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/6/1
 */
@Data
public class PageRankResponse<T>  {

    /**
     * 总数
     */
    private long total = 0;


    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;


    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 更新时间的字符串
     */
    private String modifyTimeStr;

    /**
     * 记录信息
     */
    private List<T> records;


    public PageRankResponse() {
    }



    public PageRankResponse(Page<T> page, TaskInfo taskInfo) {
        this.total = page.getTotal();
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.records = page.getRecords();
        if (taskInfo != null){
            this.modifyTime = taskInfo.getUpdateTime();
            this.modifyTimeStr = DateUtil.format(taskInfo.getUpdateTime(),"yyyy-MM-dd HH:mm");
        }
    }
}
