package com.lakala.dao.manual.mapper;

import com.lakala.dao.manual.model.MalMenu;

import java.util.List;

/**
 * Created by user on 2017/3/24.
 */
public interface MalMenuMapper {

    List<MalMenu> getMenuListByUserId(String userId);

}
