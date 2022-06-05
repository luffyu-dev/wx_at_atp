package com.rubber.wx.at.atp.service.query.wta;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import com.rubber.wx.at.atp.dao.dal.IWtaRankInfoDal;
import com.rubber.wx.at.atp.dao.entity.AtpRankInfoEntity;
import com.rubber.wx.at.atp.dao.entity.TaskInfo;
import com.rubber.wx.at.atp.dao.entity.WtaRankInfoEntity;
import com.rubber.wx.at.atp.service.admin.task.TaskTypeEnums;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import com.rubber.wx.at.atp.service.dto.response.PageRankResponse;
import com.rubber.wx.at.atp.service.query.task.TaskQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Wta排名的搜索服务
 *
 * @author luffyu
 * Created on 2022/5/21
 */
@Service
public class WtaRankQueryService {

    @Autowired
    private IWtaRankInfoDal iWtaRankInfoDal;

    @Autowired
    private TaskQueryService taskQueryService;


    /**
     * 分页查询数据
     * @param queryDto
     * @return
     */
    public PageRankResponse<WtaRankInfoEntity> queryByPage(SearchQueryDto queryDto){
        TaskInfo rankTask = taskQueryService.getValidTask(TaskTypeEnums.WTA_RANK);

        Page<WtaRankInfoEntity> page = new Page<>();
        page.setCurrent(queryDto.getPage());
        page.setSize(queryDto.getSize());
        QueryWrapper<WtaRankInfoEntity> qw = new QueryWrapper<>();
        LambdaQueryWrapper<WtaRankInfoEntity> lambda = qw.lambda();
        if (rankTask != null && StrUtil.isNotBlank(rankTask.getDataVersion())){
            lambda.eq(WtaRankInfoEntity::getDateVersion,rankTask.getDataVersion());
        }
        if (StrUtil.isNotBlank(queryDto.getSearchValue())) {
            lambda.and(wq->
                            wq.like(WtaRankInfoEntity::getChinaFullName, "%" + queryDto.getSearchValue() + "%")
                            .or()
                            .like(WtaRankInfoEntity::getNationChineseName, "%" + queryDto.getSearchValue() + "%")
                    );
        }
        lambda.orderByAsc(WtaRankInfoEntity::getRank);
        iWtaRankInfoDal.page(page,qw);
        return new PageRankResponse<>(page,rankTask);
    }


}
