package com.rubber.wx.at.atp.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.dao.mapper.AtpRankInfoMapper;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ATP球员的排名表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-20
 */
@Service
public class AtpRankInfoDalImpl extends ServiceImpl<AtpRankInfoMapper, AtpRankInfoEntity> implements IAtpRankInfoDal {



    /**
     * 通过第三方的业务id来查询
     *
     * @param playerId    第三方的业务id
     * @param dateVersion
     * @return 返回结果
     */
    @Override
    public AtpRankInfoEntity getByPlayerId(String playerId, String dateVersion) {
        QueryWrapper<AtpRankInfoEntity> qw = new QueryWrapper<>();
        qw.lambda().eq(AtpRankInfoEntity::getPlayerId,playerId)
                .eq(AtpRankInfoEntity::getDateVersion,dateVersion);
        return getOne(qw);
    }
}
