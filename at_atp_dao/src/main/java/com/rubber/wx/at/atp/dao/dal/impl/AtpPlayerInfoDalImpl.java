package com.rubber.wx.at.atp.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.dal.IAtpPlayerInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.mapper.AtpPlayerInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ATP运动员详情表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-14
 */
@Service
public class AtpPlayerInfoDalImpl extends ServiceImpl<AtpPlayerInfoMapper, AtpPlayerInfoEntity> implements IAtpPlayerInfoDal {

    /**
     * 通过第三方的业务id来查询
     *
     * @param thirdId 第三方的业务id
     * @return 返回结果
     */
    @Override
    public AtpPlayerInfoEntity getByPlayerId(String thirdId) {
        QueryWrapper<AtpPlayerInfoEntity> qw = new QueryWrapper<>();
        qw.lambda().eq(AtpPlayerInfoEntity::getPlayerId,thirdId);
        return getOne(qw);
    }
}
