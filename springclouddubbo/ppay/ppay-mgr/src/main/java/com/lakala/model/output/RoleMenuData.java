package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/30.
 */
@Data
public class RoleMenuData {
    public Integer roleId;
    public List<RoleMenuVO> roleMenuList;

}
