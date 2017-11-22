package com.lakala.dao.manual.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/27.
 */
@Data
public class MalUser {
    private BigDecimal userId;
    private BigDecimal roleId;
    private String roleName;
    private String username;
    private String realName;
    private String phone;
    private String email;
    private BigDecimal gender;
    private BigDecimal status;
}
