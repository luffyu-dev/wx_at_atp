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
 * ATP球员的排名表
 * </p>
 *
 * @author luffyu
 * @since 2022-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_wta_rank_info")
public class WtaRankInfoEntity implements Serializable {

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
     * 运动员id
     */
    @TableField("Fthird_id")
    private String thirdId;

    /**
     * 中文全称
     */
    @TableField("Fchina_full_name")
    private String chinaFullName;

    /**
     * 国籍名称（中）
     */
    @TableField("Fnation_chinese_name")
    private String nationChineseName;

    /**
     * 国籍图片
     */
    @TableField("Fnation_img")
    private String nationImg;

    /**
     * 积分
     */
    @TableField("Fpoint")
    private Integer point;

    /**
     * 本周失效积分
     */
    @TableField("Fflop_point")
    private Integer flopPoint;

    /**
     * 本周新增积分
     */
    @TableField("Fwin_point")
    private Integer winPoint;

    /**
     * 排名
     */
    @TableField("Frank")
    private Integer rank;

    /**
     * 积分
     */
    @TableField("Frank_change")
    private Integer rankChange;

    /**
     * 官方排名
     */
    @TableField("Fofficial_rank")
    private Integer officialRank;

    /**
     * 最佳排名
     */
    @TableField("Fhighest_rank")
    private Integer highestRank;

    /**
     * 胜率
     */
    @TableField("Fcycle_win_pro")
    private String cycleWinPro;

    /**
     * 胜场
     */
    @TableField("Fcycle_win_num")
    private Integer cycleWinNum;

    /**
     * 负场
     */
    @TableField("Fcycle_lose_num")
    private Integer cycleLoseNum;

    /**
     * 奖金
     */
    @TableField("Fcycle_bonus")
    private String cycleBonus;

    /**
     * 日期版本
     */
    @TableField("Fdate_version")
    private String dateVersion;

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
     * 状态 1表示正常
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
