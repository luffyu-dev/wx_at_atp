package com.rubber.wx.at.atp.service.query.atp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import com.rubber.wx.at.atp.service.dto.response.PageRankResponse;
import com.rubber.wx.at.atp.service.query.task.TaskQueryService;
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
public class AtpRankQueryService {

    @Autowired
    private IAtpRankInfoDal iAtpRankInfoDal;

    @Autowired
    private TaskQueryService taskQueryService;

    /**
     * 分页查询数据
     * @param queryDto
     * @return
     */
    public PageRankResponse<AtpRankInfoEntity> queryByPage(SearchQueryDto queryDto){
        TaskInfo rankTask = taskQueryService.getValidTask(TaskTypeEnums.ATP_RANK);
        Page<AtpRankInfoEntity> page = new Page<>();
        page.setCurrent(queryDto.getPage());
        page.setSize(queryDto.getSize());
        page.setOptimizeCountSql(false);
        page.setSearchCount(false);
        QueryWrapper<AtpRankInfoEntity> qw = new QueryWrapper<>();
        LambdaQueryWrapper<AtpRankInfoEntity> lambda = qw.lambda();
        if (rankTask != null && StrUtil.isNotBlank(rankTask.getDataVersion())){
            lambda.eq(AtpRankInfoEntity::getDateVersion,rankTask.getDataVersion());
        }
        if (StrUtil.isNotBlank(queryDto.getSearchValue())) {
            lambda.and(wq ->
                        wq.like(AtpRankInfoEntity::getChinaFullName, "%" + queryDto.getSearchValue() + "%")
                        .or()
                        .like(AtpRankInfoEntity::getNationChineseName, "%" + queryDto.getSearchValue() + "%")
                    );
        }
        qw.lambda().orderByAsc(AtpRankInfoEntity::getRank);
        iAtpRankInfoDal.page(page,qw);
        return new PageRankResponse<>(page,rankTask);
    }


}
