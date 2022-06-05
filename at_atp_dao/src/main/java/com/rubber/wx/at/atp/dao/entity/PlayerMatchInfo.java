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
 * 球员的比赛记录表
 * </p>
 *
 * @author luffyu
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_player_match_info")
public class PlayerMatchInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 运动员id
     */
    @TableField("Fplayer_id")
    private String playerId;

    /**
     * 比赛名称
     */
    @TableField("Fmatch_name")
    private String matchName;

    /**
     * 比赛年份
     */
    @TableField("Fmatch_year")
    private String matchYear;

    /**
     * 比赛类型 大满贯/大师赛/500分赛
     */
    @TableField("Fmatch_type")
    private String matchType;

    /**
     * 比赛所得积分
     */
    @TableField("Fmatch_point")
    private Integer matchPoint;

    /**
     * 比赛结果
     */
    @TableField("Fmatch_result")
    private String matchResult;

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
     * 状态 0表示失效 1表示有效
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
