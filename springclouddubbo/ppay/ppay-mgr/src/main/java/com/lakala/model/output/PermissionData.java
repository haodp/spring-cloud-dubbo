package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/4/7.
 */
@Data
public class PermissionData extends BaseData {

    List<PermissionVO> permissionList;
}
