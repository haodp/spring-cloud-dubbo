package com.lakala.service;

import com.lakala.common.constant.EFlag;
import com.lakala.common.constant.OrderByConsts;
import com.lakala.common.constant.SysConsts;
import com.lakala.common.util.ToolUtils;
import com.lakala.dao.gen.mapper.TMenuMapper;
import com.lakala.dao.gen.model.TMenu;
import com.lakala.dao.gen.model.TMenuExample;
import com.lakala.dao.manual.mapper.MalMenuMapper;
import com.lakala.dao.manual.model.MalMenu;
import com.lakala.model.input.MenuModel;
import com.lakala.model.output.MenuData;
import com.lakala.model.output.MenuVO;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by user on 2017/3/24.
 */
@Transactional
@Service
public class MenuService extends BaseService {

    @Autowired
    private TMenuMapper menuMapper;

    @Autowired
    private MalMenuMapper malMenuMapper;

    /**
     * 根据用户ID获取菜单列表
     *
     * @param userId {@link Integer}
     * @return List
     */
    public List<MenuVO> getMenuListByUserId(Integer userId) {
        List<MalMenu> list = malMenuMapper.getMenuListByUserId(String.valueOf(userId));
        List<MenuVO> menuList = formatMenuList(list);
        return menuList;
    }

    /**
     * 一级父菜单
     *
     * @param list {@link List}
     * @return List
     */
    private List<MenuVO> formatMenuList(List<MalMenu> list) {
        List<MenuVO> menuList = new ArrayList<>();
        list.forEach(item -> {
            if (Objects.equals(item.getParentId(), SysConsts.ZERO)) {
                MenuVO menu = new MenuVO();
                menu.setAction(item.getAction());
                menu.setMenuId(item.getMenuId().toString());
                menu.setParentId(item.getParentId());
                menu.setMenuname(item.getMenuname());
                menu.setNodes(getNodes(item.getMenuId().toString(), list));

                menuList.add(menu);
            }
        });

        return menuList;
    }

    /**
     * 子菜单递归方法
     *
     * @param parentId {@link Integer}
     * @param list     {@link List}
     * @return List
     */
    private List<MenuVO> getNodes(String parentId, List<MalMenu> list) {

        List<MenuVO> nodeList = new ArrayList<>();
        for (MalMenu menu : list) {

            if (Objects.equals(menu.getParentId(), SysConsts.ZERO) || Objects.equals(parentId, SysConsts.ZERO)) {
                continue;
            }

            if (parentId.equals(menu.getParentId())) {
                MenuVO node = new MenuVO();
                node.setMenuId(menu.getMenuId().toString());
                node.setAction(menu.getAction());
                node.setFlag(menu.getFlag());
                node.setMenuname(menu.getMenuname());
                node.setNodes(getNodes(menu.getMenuId().toString(), list));
                nodeList.add(node);
            }
        }

        return nodeList;
    }

    /**
     * 获取所有可用菜单列表 分页
     *
     * @param menuModel {@link MenuModel}
     * @return MenuData
     */
    public MenuData getMenuData(MenuModel menuModel) {

        List<MenuVO> menuVOList = Lists.newArrayList();

        TMenuExample menuExample = new TMenuExample();
//        menuExample.createCriteria().andFlagEqualTo( new BigDecimal(EFlag.NORMAL.getValue()) );
//        Integer begin = (menuModel.getPageNo() - SysConsts.ONE) * menuModel.getPageSize();
//        Page page = new Page(begin, menuModel.getPageSize());
//        menuExample.setPage(page);
        menuExample.setOrderByClause(OrderByConsts.ORDER_BY_ID);
        List<TMenu> menuList = menuMapper.selectByExample(menuExample);

        long totalCount = menuMapper.countByExample(menuExample);
        MenuData menuData = new MenuData();
        menuData.setTotalCount((int) totalCount);
        Integer totalPage = (int) Math.round(Math.ceil((double) totalCount / menuModel.getPageSize()));
        menuData.setTotalPage(totalPage);

        menuList.forEach(menu -> {
            MenuVO vo = getMenuVO(menu);
            menuVOList.add(vo);
        });

        menuData.setMenuList(menuVOList);

        return menuData;
    }

    /**
     * 获取所有可用菜单列表
     *
     * @return MenuData
     */
    public MenuData getAllMenuData() {

        List<MenuVO> menuVOList = Lists.newArrayList();

        TMenuExample menuExample = new TMenuExample();
        menuExample.createCriteria().andFlagEqualTo(new BigDecimal(EFlag.NORMAL.getValue()));
        menuExample.setOrderByClause(OrderByConsts.ORDER_BY_ID);
        List<TMenu> menuList = menuMapper.selectByExample(menuExample);
        menuList.forEach(menu -> {
            MenuVO vo = getMenuVO(menu);
            menuVOList.add(vo);
        });
        MenuData menuData = new MenuData();
        menuData.setMenuList(menuVOList);

        return menuData;
    }

    /**
     * 封装vo
     *
     * @param menu {@link TMenu}
     * @return MenuVO
     */
    private MenuVO getMenuVO(TMenu menu) {
        MenuVO vo = new MenuVO();

        BeanUtils.copyProperties(menu, vo);
        vo.setMenuId(menu.getUuid());
        vo.setFlag(menu.getFlag());
        vo.setSort(menu.getRowSort());
        if (Objects.isNull(menu.getCreateTime())) {
            vo.setCreateTime(StringUtils.EMPTY);
        } else {
            vo.setCreateTime(ToolUtils.DATEFORMATTER.format(menu.getCreateTime()));
        }

        return vo;

    }

    /**
     * 创建菜单
     *
     * @param menuModel {@link MenuModel}
     * @return boolean
     */
    public boolean createMenu(MenuModel menuModel) {
        boolean isSuccess = false;

        TMenu menu = new TMenu();
        BeanUtils.copyProperties(menuModel, menu);
//        menu.setSort((byte) menuModel.getSort().intValue());
//        menu.setFlag((byte) EFlag.NORMAL.getValue().intValue());
//        menu.setCreateUserId(getLoginUser().getId());
        menu.setCreateTime(new Date());
        int result = menuMapper.insertSelective(menu);
        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 修改菜单
     *
     * @param menuModel {@link MenuModel}
     * @return boolean
     */
    public boolean updateMenu(MenuModel menuModel) {
        boolean isSuccess = false;

        TMenu menu = new TMenu();
        BeanUtils.copyProperties(menuModel, menu);
        menu.setModifyTime(new Date());
//        menu.setModifyUserId(getLoginUser().getId());
        int result = menuMapper.updateByPrimaryKeySelective(menu);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 删除菜单
     *
     * @param menuModel {@link MenuModel}
     * @return boolean
     */
    public boolean deleteMenu(MenuModel menuModel) {
        boolean isSuccess = false;

        TMenu menu = new TMenu();
        BeanUtils.copyProperties(menuModel, menu);
        menu.setModifyTime(new Date());
//        menu.setModifyUserId(getLoginUser().getId());
//        menu.setFlag((byte) EFlag.DELETE.getValue().intValue());
        int result = menuMapper.updateByPrimaryKeySelective(menu);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

}
