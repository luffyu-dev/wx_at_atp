package com.rubber.wx.at.atp.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.entity.PlayerMatchInfo;
import com.rubber.wx.at.atp.dao.mapper.PlayerMatchInfoMapper;
import com.rubber.wx.at.atp.dao.dal.IPlayerMatchInfoDal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 球员的比赛记录表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-28
 */
@Service
public class PlayerMatchInfoDalImpl extends ServiceImpl<PlayerMatchInfoMapper, PlayerMatchInfo> implements IPlayerMatchInfoDal {


    /**
     * 通过关键参数查询信息
     *
     * @param playerId
     */
    @Override
    public List<PlayerMatchInfo> queryAllMatchByPlayerId(String playerId) {
        QueryWrapper<PlayerMatchInfo> qw = new QueryWrapper<>();
        qw.lambda().eq(PlayerMatchInfo::getPlayerId, playerId)
                .eq(PlayerMatchInfo::getStatus, 1);
        return list(qw);
    }

}
