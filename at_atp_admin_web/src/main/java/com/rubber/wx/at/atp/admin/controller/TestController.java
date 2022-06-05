package com.rubber.wx.at.atp.admin.controller;

import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.wx.at.atp.dao.dal.IAtpRankInfoDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2022/5/29
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private IAtpRankInfoDal iAtpRankInfoDal;


    @PostMapping("/rank/{id}")
    public ResultMsg taskAtpInfo(@PathVariable("id") String id)  {
        return ResultMsg.success(iAtpRankInfoDal.getById(id));
    }

}
