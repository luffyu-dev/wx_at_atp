package com.rubber.wx.at.atp.api.controller.wta;

import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.wx.at.atp.service.dto.query.SearchQueryDto;
import com.rubber.wx.at.atp.service.query.wta.WtaPlayerQueryService;
import com.rubber.wx.at.atp.service.query.wta.WtaRankQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luffyu
 * Created on 2022/5/21
 */
@RestController
@RequestMapping("/wta")
public class PlayerInfoQueryController {

    @Autowired
    private WtaRankQueryService wtaRankQueryService;


    @Autowired
    private WtaPlayerQueryService wtaPlayerQueryService;


    /**
     * 排名搜索
     */
    @PostMapping("/rank/search")
    public ResultMsg queryRankByPage(@RequestBody SearchQueryDto dto){
        return ResultMsg.success(wtaRankQueryService.queryByPage(dto));
    }

    /**
     * 用户搜索
     */
    @PostMapping("/player/search")
    public ResultMsg queryPlayerByPage(@RequestBody SearchQueryDto dto){
        return ResultMsg.success(wtaPlayerQueryService.queryByPage(dto));
    }







}
