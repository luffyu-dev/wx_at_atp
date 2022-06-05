package com.rubber.wx.at.atp.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.entity.WtaPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.mapper.WtaPlayerInfoMapper;
import com.rubber.wx.at.atp.dao.dal.IWtaPlayerInfoDal;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * WTA运动员详情表 服务实现类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-21
 */
@Service
public class WtaPlayerInfoDalImpl extends ServiceImpl<WtaPlayerInfoMapper, WtaPlayerInfoEntity> implements IWtaPlayerInfoDal {

    /**
     * 通过第三方的业务id来查询
     *
     * @param thirdId 第三方的业务id
     * @return 返回结果
     */
    @Override
    public WtaPlayerInfoEntity getByPlayerId(String thirdId) {
        QueryWrapper<WtaPlayerInfoEntity> qw = new QueryWrapper<>();
        qw.lambda().eq(WtaPlayerInfoEntity::getPlayerId,thirdId);
        return getOne(qw);
    }
}
