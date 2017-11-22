package com.lakala.dao.manual.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by user on 2017/4/10.
 */
public interface MalPermissionMapper {
    List<String> getPermissionListByUserId(@Param("userId") Integer userId);
}
