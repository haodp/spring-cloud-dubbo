package com.lakala.dao.manual.mapper;

import java.util.List;

/**
 * Created by user on 2017/3/30.
 */
public interface MalRoleMapper {

    Integer insertRoleMenuBatch(List roleMenuList);
    Integer insertRolePermBatch(List roleMenuList);
}
