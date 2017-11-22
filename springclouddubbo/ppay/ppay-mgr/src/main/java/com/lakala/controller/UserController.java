package com.lakala.controller;

import com.lakala.common.constant.ECode;
import com.lakala.common.constant.MsgConsts;
import com.lakala.model.input.UserModel;
import com.lakala.model.output.Result;
import com.lakala.model.output.UserData;
import com.lakala.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by user on 2017/3/27.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    /**
     * 用户列表查询
     *
     * @param userModel {@link UserModel}
     * @return Result
     */
    @RequestMapping("/getUserList")
    public Result getUserList(@RequestBody UserModel userModel) {
        UserData userData = userService.getUserData(userModel);
        Result result = new Result();
        result.setCode(ECode.OK.getCode());
        result.setMsg(MsgConsts.GET_USER_SUCCESS);
        result.setData(userData);
        return result;
    }

    /**
     * 创建用户
     *
     * @param userModel {@link UserModel}
     * @return Result
     */
    @RequestMapping("/createUser")
    public Result createUser(@RequestBody UserModel userModel) {
        Result result = new Result();
        //TODO check userModel

        //save
        boolean isSuccess = userService.createUser(userModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.CREATE_USER_SUCCESS);
        }

        return result;
    }

    /**
     * 更新用户
     *
     * @param userModel {@link UserModel}
     * @return Result
     */
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody UserModel userModel) {
        Result result = new Result();
        //TODO check userModel

        //update
        boolean isSuccess = userService.updateUser(userModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.UPDATE_USER_SUCCESS);
        }

        return result;
    }

    /**
     * 删除用户
     *
     * @param userModel {@link UserModel}
     * @return Result
     */
    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestBody UserModel userModel) {
        Result result = new Result();
        //TODO check userModel

        //delete
        boolean isSuccess = userService.deleteUser(userModel);
        if (isSuccess) {
            result.setCode(ECode.OK.getCode());
            result.setMsg(MsgConsts.DELETE_USER_SUCCESS);
        }

        return result;
    }
}


