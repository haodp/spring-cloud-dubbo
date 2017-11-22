package com.lakala.service;

import com.lakala.common.constant.SessionConsts;
import com.lakala.common.constant.SysConsts;
import com.lakala.model.output.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Objects;

/**
 * Created by user on 2017/4/10.
 */
public abstract class BaseService {

    public UserInfo getLoginUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(SysConsts.ONE);
        Subject subject = SecurityUtils.getSubject();
        Object sessionUserInfo = subject.getSession().getAttribute(SessionConsts.SESSION_USER_INFO);

        if (Objects.nonNull(sessionUserInfo) && sessionUserInfo instanceof UserInfo) {
            userInfo = (UserInfo) sessionUserInfo;
        }

        return userInfo;
    }
}
