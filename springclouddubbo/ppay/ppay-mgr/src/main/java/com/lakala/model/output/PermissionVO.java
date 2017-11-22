package com.lakala.model.output;

import lombok.Data;

/**
 * Created by user on 2017/4/7.
 */
@Data
public class PermissionVO {
    private Integer id;
    private Integer menuId;
    private String permname;
    private String action;
    private String createTime;
    private boolean checked;
}
