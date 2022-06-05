package com.rubber.wx.at.atp.api.controller.atp;

import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.wx.at.atp.service.query.atp.AtpPlayerQueryService;
import com.rubber.wx.at.atp.service.query.atp.AtpRankQueryService;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luffyu
 * Created on 2022/5/21
 */
@RestController
@RequestMapping("/atp")
public class RankInfoQueryController {

    @Autowired
    private AtpRankQueryService atpRankQueryService;


    @Autowired
    private AtpPlayerQueryService atpPlayerQueryService;


    /**
     * 排名搜索
     */
    @PostMapping("/rank/search")
    public ResultMsg queryRankByPage(@RequestBody SearchQueryDto dto){
        return ResultMsg.success(atpRankQueryService.queryByPage(dto));
    }

    /**
     * 用户搜索
     */
    @PostMapping("/player/search")
    public ResultMsg queryPlayerByPage(@RequestBody SearchQueryDto dto){
        return ResultMsg.success(atpPlayerQueryService.queryByPage(dto));
    }


}
