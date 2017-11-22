package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/30.
 */
@Data
public class RoleMenuVO {
    private Integer menuId;
    private Integer parentId;
    private String menuname;
    private String action;
    private boolean checked;
    private List<PermissionVO> permList;
}
