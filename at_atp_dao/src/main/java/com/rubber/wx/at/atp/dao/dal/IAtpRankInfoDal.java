package com.rubber.wx.at.atp.dao.dal;

import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ATP球员的排名表 服务类
 * </p>
 *
 * @author luffyu
 * @since 2022-05-20
 */
public interface IAtpRankInfoDal extends IService<AtpRankInfoEntity> {





    /**
     * 通过第三方的业务id来查询
     * @param playerId 第三方的业务id
     * @return 返回结果
     */
    AtpRankInfoEntity getByPlayerId(String playerId,String dateVersion);

}
