package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/30.
 */
@Data
public class RoleVO {
    private Integer id;
    private String rolename;
    private String roleStatus;
    private String roleCreateTime;
    public List<RoleMenuVO> roleMenuList;
}
