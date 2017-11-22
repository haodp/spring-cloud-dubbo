package com.lakala.dao.manual.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by user on 2017/3/24.
 */
@Data
public class MalMenu {
    private BigDecimal menuId;
    private BigDecimal parentId;
    private String menuname;
    private String action;
    private BigDecimal sort;
    private BigDecimal flag;
    private BigDecimal roleId;
    private BigDecimal userId;
    private String username;
    private String realName;
    private BigDecimal userStatus;

}
