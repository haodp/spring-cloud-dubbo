package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/4/1.
 */
@Data
public class MenuData extends BaseData {
    List<MenuVO> menuList;
}
