package com.rubber.wx.at.atp.service.dto.query;

import com.rubber.wx.at.atp.service.dto.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/5/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchQueryDto extends PageDto {

    /**
     * 搜索的值
     */
    private String searchValue;



}
