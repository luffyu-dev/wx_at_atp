package com.rubber.wx.at.atp.dao.dal;

import com.rubber.wx.at.atp.dao.entity.PlayerMatchInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 球员的比赛记录表 服务类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-28
 */
public interface IPlayerMatchInfoDal extends IService<PlayerMatchInfo> {

    /**
     * 通过关键参数查询信息
     */
    List<PlayerMatchInfo> queryAllMatchByPlayerId(String playerId);

}
