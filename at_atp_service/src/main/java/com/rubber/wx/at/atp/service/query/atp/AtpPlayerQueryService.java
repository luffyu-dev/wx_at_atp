package com.rubber.wx.at.atp.service.query.atp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.dal.IAtpPlayerInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpPlayerInfoEntity;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Atp排名的搜索服务
 *
 * @author luffyu
 * Created on 2022/5/21
 */
@Service
public class AtpPlayerQueryService {

    @Autowired
    private IAtpPlayerInfoDal iAtpPlayerInfoDal;

    /**
     * 分页查询数据
     * @param queryDto
     * @return
     */
    public Page<AtpPlayerInfoEntity> queryByPage(SearchQueryDto queryDto){
        Page<AtpPlayerInfoEntity> page = new Page<>();
        page.setCurrent(queryDto.getPage());
        page.setSize(queryDto.getSize());
        QueryWrapper<AtpPlayerInfoEntity> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(queryDto.getSearchValue())) {
            qw.lambda()
                    .like(AtpPlayerInfoEntity::getChinaFullName, "%" + queryDto.getSearchValue() + "%")
                    .or()
                    .like(AtpPlayerInfoEntity::getNationChineseName, "%" + queryDto.getSearchValue() + "%");
        }
        qw.lambda().orderByDesc(AtpPlayerInfoEntity::getSeqWeight);
        iAtpPlayerInfoDal.page(page,qw);
        return page;
    }


}
