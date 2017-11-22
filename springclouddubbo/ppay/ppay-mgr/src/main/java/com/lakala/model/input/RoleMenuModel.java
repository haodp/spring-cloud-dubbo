package com.lakala.model.input;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/30.
 */
@Data
public class RoleMenuModel extends BaseModel {
    private Integer roleId;
    List<MenuEntry> menuList;
}
