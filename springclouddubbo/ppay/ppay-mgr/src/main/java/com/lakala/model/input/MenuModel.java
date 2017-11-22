package com.lakala.model.input;

import lombok.Data;

/**
 * Created by user on 2017/4/1.
 */
@Data
public class MenuModel extends BaseModel {
    private Integer id;
    private Integer parentId;
    private String menuname;
    private String action;
    private Integer sort;
}