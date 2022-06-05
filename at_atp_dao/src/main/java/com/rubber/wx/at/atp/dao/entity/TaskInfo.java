package com.rubber.wx.at.atp.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务记录表
 * </p>
 *
 * @author luffyu
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_task_info")
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务类型
     */
    @TableField("Ftask_type")
    private String taskType;


    /**
     * 任务类型
     */
    @TableField("Fdata_version")
    private String dataVersion;


    /**
     * 任务类型
     */
    @TableField("Fparams")
    private String params;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 版本号
     */
    @TableField("Fversion")
    @Version
    private Integer version;

    /**
     * 状态 0表示失败 1表示成功
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("Fcreate_time")
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    @TableField("Fupdate_time")
    private LocalDateTime updateTime;


}
