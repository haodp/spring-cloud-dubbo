package com.lakala.controller;

import com.lakala.common.constant.ECode;
import com.lakala.common.constant.MsgConsts;
import com.lakala.model.input.PermissionModel;
import com.lakala.model.output.PermissionData;
import com.lakala.model.output.Result;
import com.lakala.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 2017/4/7.
 */

@RestController
public class PermissionController extends BaseController {

    @Autowired
    PermissionService permissionService;

    /**
     * 获取权限列表
     *
     * @param permissionModel {@link PermissionModel}
     * @return Result
     */
    @PostMapping("/getPermissionList")
    public Result getPermissionList(@RequestBody PermissionModel permissionModel) {
        Result result = new Result();
        PermissionData permData = permissionService.permissionData(permissionModel);
        result.setCode(ECode.OK.getCode());
        result.setData(permData);
        return result;
    }

    /**
     * 创建权限
     *
     * @param permissionModel {@link PermissionModel}
     * @return Result
     */
    @PostMapping("/createPermission")
    public Result createPermission(@RequestBody PermissionModel permissionModel) {
        Result result = new Result();
        boolean isSuccess = permissionService.createPermission(permissionModel);

        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.CREATE_USER_SUCCESS);
        }

        return result;
    }

    /**
     * 更新权限
     *
     * @param permissionModel {@link PermissionModel}
     * @return Result
     */
    @PostMapping("/updatePermission")
    public Result updatePermission(@RequestBody PermissionModel permissionModel) {
        Result result = new Result();
        boolean isSuccess = permissionService.updatePermission(permissionModel);

        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.CREATE_USER_SUCCESS);
        }

        return result;
    }

    /**
     * 更新权限
     *
     * @param permissionModel {@link PermissionModel}
     * @return Result
     */
    @PostMapping("/deletePermission")
    public Result deletePermission(@RequestBody PermissionModel permissionModel) {
        Result result = new Result();
        boolean isSuccess = permissionService.deletePermission(permissionModel);

        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.CREATE_USER_SUCCESS);
        }

        return result;
    }
}
