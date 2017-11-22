package com.lakala.model.input;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/30.
 */
@Data
public class MenuEntry {
    private Integer menuId;
    private Integer parentId;
    private String menuname;
    private String action;
    private boolean checked;
    private List<PermEntry> permList;
}
