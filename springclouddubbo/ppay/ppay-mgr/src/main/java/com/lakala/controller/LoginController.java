package com.lakala.controller;

import com.lakala.common.constant.ECode;
import com.lakala.common.constant.MsgConsts;
import com.lakala.model.input.UserLoginModel;
import com.lakala.model.output.MenuVO;
import com.lakala.model.output.Result;
import com.lakala.model.output.UserInfo;
import com.lakala.service.MenuService;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by user on 2017/4/6.
 */
@RestController
public class LoginController extends BaseController {
    @Autowired
    MenuService menunService;

    /**
     * 用户登录
     *
     * @param request        {@link HttpServletRequest}
     * @param userLoginModel {@link UserLoginModel}
     * @return Result
     */
    @RequestMapping("/userLogin")
    public Result userLogin(HttpServletRequest request, @RequestBody UserLoginModel userLoginModel) {
        Result result = new Result();

        UsernamePasswordToken token = new UsernamePasswordToken(userLoginModel.getUsername(), userLoginModel.getPassword());
        Subject subject = getSubject();

        try {
            token.setRememberMe(userLoginModel.isRemember());
            //用户登录调用ShiroRealm.doGetAuthenticationInfo
            subject.login(token);

            if (subject.isAuthenticated() || subject.isRemembered()) {
                if (Objects.nonNull(subject.getPrincipals())) {
                    UserInfo userInfo = (UserInfo) subject.getPrincipals().getPrimaryPrincipal();
                    if (Objects.nonNull(userInfo)) {
                        setLoginUser(userInfo);
                        List<MenuVO> menuList = menunService.getMenuListByUserId(userInfo.getId());
                        setMenuList(menuList);
                        result.setCode(ECode.OK.getCode());
                        result.setMsg(MsgConsts.SUCCESS);
                    }
                }
            }

        } catch (UnknownAccountException e) {
            result.setCode(ECode.FAILED.getCode());
            result.setMsg(MsgConsts.NON_EXISTENT_USER);
        } catch (IncorrectCredentialsException e) {
            result.setCode(ECode.FAILED.getCode());
            result.setMsg(MsgConsts.INCORRECT_CREDENTIALS);
        }

        return result;
    }

}
