package com.rubber.wx.at.atp.dao.dal;

import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ATP运动员详情表 服务类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-14
 */
public interface IAtpPlayerInfoDal extends IService<AtpPlayerInfoEntity> {

    /**
     * 通过第三方的业务id来查询
     * @param thirdId 第三方的业务id
     * @return 返回结果
     */
    AtpPlayerInfoEntity getByPlayerId(String thirdId);
}
