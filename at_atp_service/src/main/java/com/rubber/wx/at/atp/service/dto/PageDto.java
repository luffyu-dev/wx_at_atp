package com.rubber.wx.at.atp.service.dto;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/5/21
 */
@Data
public class PageDto {

    /**
     * 当前页
     */
    private int page = 1;

    /**
     * 每页大小
     */
    private int size = 20;

}
