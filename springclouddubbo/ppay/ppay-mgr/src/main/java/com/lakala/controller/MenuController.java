package com.lakala.controller;

import com.lakala.common.constant.ECode;
import com.lakala.model.input.MenuModel;
import com.lakala.model.output.MenuData;
import com.lakala.model.output.Result;
import com.lakala.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 2017/4/1.
 */

@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 获取所有菜单列表
     *
     * @param menuModel {@link MenuModel}
     * @return Result
     */
    @RequestMapping("/getMenuList")
    public Result getMenuList(@RequestBody MenuModel menuModel) {
        Result result = new Result();
        MenuData menuData = menuService.getMenuData(menuModel);
        result.setCode(ECode.OK.getCode());
        result.setData(menuData);

        return result;
    }

    /**
     * 获取所有菜单列表 父菜单
     *
     * @return Result
     */
    @RequestMapping("/getAllMenuList")
    public Result getAllMenuList() {
        Result result = new Result();
        MenuData menuData = menuService.getAllMenuData();
        result.setCode(ECode.OK.getCode());
        result.setData(menuData);

        return result;
    }

    /**
     * 创建菜单
     * return  Result
     */
    @RequestMapping("/createMenu")
    public Result createMenu(@RequestBody MenuModel menuModel) {
        menuService.createMenu(menuModel);
        Result result = new Result();
        result.setCode(ECode.OK.getCode());

        return result;
    }

    /**
     * 更新菜单
     *
     * @param menuModel {@link MenuModel}
     * @return Result
     */
    @RequestMapping("/updateMenu")
    public Result updateMenu(@RequestBody MenuModel menuModel) {
        menuService.updateMenu(menuModel);
        Result result = new Result();
        result.setCode(ECode.OK.getCode());

        return result;
    }

    /**
     * 删除菜单
     *
     * @param menuModel {@link MenuModel}
     * @return Result
     */
    @RequestMapping("/deleteMenu")
    public Result deleteMenu(@RequestBody MenuModel menuModel) {
        menuService.deleteMenu(menuModel);
        Result result = new Result();
        result.setCode(ECode.OK.getCode());

        return result;
    }


}
