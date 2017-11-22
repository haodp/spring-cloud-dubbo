package com.lakala.controller;

import com.lakala.common.constant.ECode;
import com.lakala.common.constant.MsgConsts;
import com.lakala.dao.gen.model.TRole;
import com.lakala.model.input.RoleMenuModel;
import com.lakala.model.input.RoleModel;
import com.lakala.model.output.Result;
import com.lakala.model.output.RoleData;
import com.lakala.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by user on 2017/3/28.
 */
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 查询用户列表中角色列表菜单
     *
     * @return Result
     */
    @RequestMapping("/getRoleListForUser")
    public Result getRoleListForUser() {
        List<TRole> list = roleService.getRoleListForUser();
        Result result = new Result();
        result.setCode(ECode.OK.getCode());
        result.setMsg(MsgConsts.GET_ROLE_SUCCESS);
        result.setData(list);
        return result;
    }

    /**
     * 查询角色列表
     *
     * @return Result
     */
    @RequestMapping("/getRoleList")
    public Result getUserList(@RequestBody RoleModel roleModel) {
        //List<Role> list = roleService.getRoleList();
        RoleData roleData = roleService.getRoleData(roleModel);
        Result result = new Result();
        result.setCode(ECode.OK.getCode());
        result.setMsg(MsgConsts.GET_ROLE_SUCCESS);
        result.setData(roleData);
        return result;
    }

    /**
     * 创建角色
     *
     * @param roleModel {@link RoleModel}
     * @return result
     */
    @RequestMapping("/createRole")
    public Result createRole(@RequestBody RoleModel roleModel) {
        Result result = new Result();
        //TODO check roleModel

        //save
        boolean isSuccess = roleService.createRole(roleModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.CREATE_ROLE_SUCCESS);
        }

        return result;
    }

    /**
     * 更新角色目录
     *
     * @param roleMenuModel {@link RoleMenuModel}
     * @return Result
     */
    @RequestMapping("/updateRoleMenu")
    public Result updateRoleMenu(@RequestBody RoleMenuModel roleMenuModel) {
        Result result = new Result();

        //update
        boolean isSuccess = roleService.updateRoleMenu(roleMenuModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.UPDATE_ROLE_SUCCESS);
        }

        return result;
    }

    /**
     * 删除角色
     *
     * @param roleModel {@link RoleModel}
     * @return Result
     */
    @RequestMapping("/deleteRole")
    public Result deleteRole(@RequestBody RoleModel roleModel) {
        Result result = new Result();

        //delete
        boolean isSuccess = roleService.deleteRole(roleModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.DELETE_ROLE_SUCCESS);
        }

        return result;
    }


}
