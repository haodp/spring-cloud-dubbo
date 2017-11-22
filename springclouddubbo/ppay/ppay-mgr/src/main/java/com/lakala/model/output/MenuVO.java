package com.lakala.model.output;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/26.
 */
@Data
public class MenuVO {
    private String menuId;
    private BigDecimal parentId;
    private String menuname;
    private String action;
    private BigDecimal flag;
    private BigDecimal sort;
    List<MenuVO> nodes;
    private String createTime;
}
