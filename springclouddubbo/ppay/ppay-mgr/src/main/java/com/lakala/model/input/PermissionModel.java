package com.lakala.model.input;

import lombok.Data;

/**
 * Created by user on 2017/4/7.
 */
@Data
public class PermissionModel extends BaseModel {

    private Integer id;
    private Integer menuId;
    private String permname;
    private String action;

}
