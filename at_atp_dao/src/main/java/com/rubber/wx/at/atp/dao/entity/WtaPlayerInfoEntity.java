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
 * WTA运动员详情表
 * </p>
 *
 * @author luffyu
 * @since 2022-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_wta_player_info")
public class WtaPlayerInfoEntity implements Serializable {

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
     * 中文名称
     */
    @TableField("Fchina_name")
    private String chinaName;

    /**
     * 中文全称
     */
    @TableField("Fchina_full_name")
    private String chinaFullName;

    /**
     * 英文第一个名称
     */
    @TableField("Ffirst_name")
    private String firstName;

    /**
     * 英文第一个名称
     */
    @TableField("Flast_name")
    private String lastName;

    /**
     * 运动员头像
     */
    @TableField("Fplayer_avatar")
    private String playerAvatar;

    /**
     * 国籍名称（中）
     */
    @TableField("Fnation_chinese_name")
    private String nationChineseName;

    /**
     * 国籍名称（英）
     */
    @TableField("Fnation_english_name")
    private String nationEnglishName;

    /**
     * 国籍图片
     */
    @TableField("Fnation_img")
    private String nationImg;

    /**
     * 生日
     */
    @TableField("Fbirthday")
    private String birthday;

    /**
     * 出生地
     */
    @TableField("Fbirth_place")
    private String birthPlace;

    /**
     * 居住地
     */
    @TableField("Fresidence")
    private String residence;

    /**
     * 升高
     */
    @TableField("Fheight")
    private String height;

    /**
     * 体重
     */
    @TableField("Fweight")
    private String weight;

    /**
     * 转职时间
     */
    @TableField("FproYear")
    private String proYear;

    /**
     * 生涯奖金数
     */
    @TableField("Fall_bonus")
    private String allBonus;

    /**
     * 个人官网
     */
    @TableField("Fwebsite")
    private String website;

    /**
     * 单打最佳排名
     */
    @TableField("Fsingles_rank_height")
    private Integer singlesRankHeight;

    /**
     * 单打冠军数
     */
    @TableField("Fsingles_champion_num")
    private Integer singlesChampionNum;

    /**
     * 双打最佳排名
     */
    @TableField("Fdouble_rank_height")
    private Integer doubleRankHeight;

    /**
     * 双打冠军数
     */
    @TableField("Fdouble_champion_num")
    private Integer doubleChampionNum;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 排序权重
     */
    @TableField("Fseq_weight")
    private String seqWeight;

    /**
     * 标签
     */
    @TableField("Ftags")
    private String tags;

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
