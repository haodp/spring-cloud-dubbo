package com.lakala.controller;

import com.lakala.common.constant.SysConsts;
import com.lakala.service.MenuService;
import com.lakala.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by user on 2017/3/22.
 */
@Controller
public class PageController extends BaseController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;
    @Autowired
    MenuService menunService;

    @RequestMapping("/")
    public String root() {
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 页面跳转控制
     *
     * @param model {@link Model}
     * @return String
     */
    @RequestMapping("/*.html")
    public String index(HttpServletRequest request, Model model) {
        String page = request.getRequestURI();
        if (!StringUtils.isEmpty(page)) {
            try {
                int first = page.lastIndexOf(SysConsts.SLASH);
                int last = page.lastIndexOf(SysConsts.DOT_HTML);
                String currentPage = page.substring(first + SysConsts.ONE, last);
                return currentPage;
            } catch (Exception e) {
                return "error-404";
            }
        }

        return "login";
    }

    /**
     * 退出登录
     *
     * @return String
     */
    @RequestMapping("/logout")
    public String logout() {
        getSubject().logout();

        return SysConsts.LOGIN_PAGE;
    }

    /**
     * 无权限
     *
     * @return String
     */
    @RequestMapping("/unauthor")
    public String unauthor() {
        return SysConsts.LOGIN_PAGE;
    }
}
