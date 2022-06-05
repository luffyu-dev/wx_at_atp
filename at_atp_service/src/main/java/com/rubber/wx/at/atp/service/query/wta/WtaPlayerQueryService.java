package com.rubber.wx.at.atp.service.query.wta;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.dal.IWtaPlayerInfoDal;
import com.rubber.wx.at.atp.dao.dal.IWtaRankInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.entity.WtaPlayerInfoEntity;
import com.rubber.wx.at.atp.dao.entity.WtaRankInfoEntity;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Wta排名的搜索服务
 *
 * @author luffyu
 * Created on 2022/5/21
 */
@Service
public class WtaPlayerQueryService {

    @Autowired
    private IWtaPlayerInfoDal iWtaPlayerInfoDal;
    /**
     * 分页查询数据
     * @param queryDto
     * @return
     */
    public Page<WtaPlayerInfoEntity> queryByPage(SearchQueryDto queryDto){
        Page<WtaPlayerInfoEntity> page = new Page<>();
        page.setCurrent(queryDto.getPage());
        page.setSize(queryDto.getSize());
        QueryWrapper<WtaPlayerInfoEntity> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(queryDto.getSearchValue())) {
            qw.lambda()
                    .like(WtaPlayerInfoEntity::getChinaFullName, "%" + queryDto.getSearchValue() + "%")
                    .or()
                    .like(WtaPlayerInfoEntity::getNationChineseName, "%" + queryDto.getSearchValue() + "%");
        }
        qw.lambda().orderByDesc(WtaPlayerInfoEntity::getSeqWeight);
        iWtaPlayerInfoDal.page(page,qw);
        return page;
    }


}
