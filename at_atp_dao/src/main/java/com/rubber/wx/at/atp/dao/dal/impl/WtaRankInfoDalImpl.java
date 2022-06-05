package com.rubber.wx.at.atp.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.dao.entity.WtaRankInfoEntity;
import com.rubber.wx.at.atp.dao.mapper.WtaRankInfoMapper;
import com.rubber.wx.at.atp.dao.dal.IWtaRankInfoDal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ATP球员的排名表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-21
 */
@Service
public class WtaRankInfoDalImpl extends ServiceImpl<WtaRankInfoMapper, WtaRankInfoEntity> implements IWtaRankInfoDal {


    /**
     * 通过第三方的业务id来查询
     *
     * @param playerId     第三方的业务id
     * @return 返回结果
     */
    @Override
    public WtaRankInfoEntity getByPlayerId(String playerId,String dataVersion) {
        QueryWrapper<WtaRankInfoEntity> qw = new QueryWrapper<>();
        qw.lambda().eq(WtaRankInfoEntity::getPlayerId,playerId)
                .eq(WtaRankInfoEntity::getDateVersion,dataVersion);
        return getOne(qw);
    }
}
