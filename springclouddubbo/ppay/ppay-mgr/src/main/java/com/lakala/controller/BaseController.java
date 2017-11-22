package com.lakala.controller;

import com.lakala.common.constant.SessionConsts;
import com.lakala.model.output.MenuVO;
import com.lakala.model.output.UserInfo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by user on 2017/4/6.
 */
public abstract class BaseController {

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        return request;
    }

    public Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }

    public HttpSession getHttpSession() {
        HttpSession session = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            session = request.getSession();
        }

        return session;
    }

    public void setLoginUser(UserInfo user) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession(true).setAttribute(SessionConsts.SESSION_USER_INFO, user);
    }

    public UserInfo getLoginUser() {
        UserInfo userInfo = null;
        Session session = getSession();
        if (session != null) {
            userInfo = (UserInfo) session.getAttribute(SessionConsts.SESSION_USER_INFO);
        }

        return userInfo;
    }

    public void setMenuList(List<MenuVO> menuList) {
        getSession().setAttribute(SessionConsts.SESSION_MENU_LIST, menuList);
    }

    public Subject getSubject() {
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }
}
