package com.lakala.config;

import com.lakala.common.constant.SysConsts;
import com.lakala.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component("urlPermissionsFilter")
public class URLPermissionsFilter extends PermissionsAuthorizationFilter {
    @Autowired
    private UserService userService;

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        String curURI = getRequestURI(request);

        Subject subject = SecurityUtils.getSubject();
        if (StringUtils.isEmpty(curURI) || mappingStatic(request) || StringUtils.endsWithAny(curURI, ".js", ".css")
                || StringUtils.endsWithAny(curURI, ".jpg", ".png", ".gif", ".jpeg", ".icon") || StringUtils.equals(curURI, "unauthor")
                || StringUtils.equals(curURI, SysConsts.LOGIN_PAGE) || StringUtils.equals(curURI, SysConsts.ACTION_USER_LOGIN)) {
            return true;
        }
        if (Objects.isNull(subject.getPrincipal())) {
            return false;
        }
        if (Objects.isNull(subject.getPrincipal()) && curURI.endsWith(SysConsts.DOT_HTML)) {
            return false;
        }

        if (Objects.nonNull(subject.getPrincipal()) && curURI.equals(SysConsts.HOME_PAGE_HTML)) {
            return true;
        }
        if (Objects.nonNull(subject.getPrincipal()) && curURI.endsWith(SysConsts.DOT_HTML)) {
            boolean isPermitted = subject.isPermitted(curURI);
            return isPermitted;
        }

        if (false) {
            if (Objects.nonNull(subject.getPrincipal())) {
                boolean isPermitted = subject.isPermitted(curURI);
                return isPermitted;
            }
        }

        return true;
    }

    private String getRequestURI(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        int first = requestURI.lastIndexOf(SysConsts.SLASH);
        String requestUrl = requestURI.substring(first + SysConsts.ONE);
        return requestUrl;
    }

    boolean mappingStatic(ServletRequest request) {
        boolean mapped = false;
        HttpServletRequest req = (HttpServletRequest) request;
        String curURL = req.getRequestURL().toString();
        if (curURL.indexOf("js/module") != -1 || curURL.indexOf("js/main") != -1) {
            mapped = true;
        }

        return mapped;
    }
}
