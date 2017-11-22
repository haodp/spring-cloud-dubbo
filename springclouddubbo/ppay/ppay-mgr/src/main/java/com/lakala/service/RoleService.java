package com.lakala.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lakala.common.constant.OrderByConsts;
import com.lakala.common.constant.SysConsts;
import com.lakala.common.util.ToolUtils;
import com.lakala.dao.gen.mapper.*;
import com.lakala.dao.gen.model.*;
import com.lakala.dao.manual.mapper.MalMenuMapper;
import com.lakala.dao.manual.mapper.MalRoleMapper;
import com.lakala.model.input.MenuEntry;
import com.lakala.model.input.PermEntry;
import com.lakala.model.input.RoleMenuModel;
import com.lakala.model.input.RoleModel;
import com.lakala.model.output.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by user on 2017/3/24.
 */

@Transactional
@Service
public class RoleService extends BaseService {

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TRoleMenuMapper roleMenuMapper;

    @Autowired
    TMenuMapper menuMapper;

    @Autowired
    MalMenuMapper malMenuMapper;

    @Autowired
    MalRoleMapper malRoleMapper;

    @Autowired
    TUserRoleMapper userRoleMapper;

    @Autowired
    TPermissionMapper permissionMapper;

    @Autowired
    TRolePermissionMapper rolePermissionMapper;

    /**
     * 用户列表中获取角色列表菜单项
     *
     * @return List
     */
    public List<TRole> getRoleListForUser() {
        TRoleExample example = new TRoleExample();
//        example.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        return roleMapper.selectByExample(example);
    }

    /**
     * 获取角色列表
     *
     * @return List
     */
    private List<TRole> getRoleList() {
        TRoleExample example = new TRoleExample();
//        example.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        return roleMapper.selectByExample(example);
    }

    /**
     * 获取角色列表
     *
     * @param roleModel {@link RoleModel}
     * @return RoleData
     */
    public RoleData getRoleData(RoleModel roleModel) {

        List<RoleVO> roleList = Lists.newArrayList();
        TRoleExample example = new TRoleExample();
//        example.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
//        Integer begin = (roleModel.getPageNo() - SysConsts.ONE) * roleModel.getPageSize();
//        Page page = new Page(begin, roleModel.getPageSize());
//        example.setPage(page);
        example.setOrderByClause(OrderByConsts.ORDER_BY_ID_DESC);
        List<TRole> dbRoleList = roleMapper.selectByExample(example);

        long totalCount = roleMapper.countByExample(example);
        RoleData roleData = new RoleData();
        roleData.setTotalCount((int) totalCount);
        Integer totalPage = (int) Math.round(Math.ceil((double) totalCount / roleModel.getPageSize()));
        roleData.setTotalPage(totalPage);

        //TODO 此处有性能问题 可优化
        dbRoleList.forEach(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
//            vo.setRoleStatus(ERoleStatus.fromValue(Byte.toUnsignedInt(role.getStatus())).getName());

            if (Objects.isNull(role.getCreateTime())) {
                vo.setRoleCreateTime(StringUtils.EMPTY);
            } else {
                vo.setRoleCreateTime(ToolUtils.DATEFORMATTER.format(role.getCreateTime()));
            }

//            vo.setRoleMenuList(getRoleMenuList(role.getUUId()));
            roleList.add(vo);
        });
        roleData.setRoleList(roleList);

        return roleData;
    }

    /**
     * 创建角色
     *
     * @param roleModel {@link RoleModel}
     * @return boolean
     */
    public boolean createRole(RoleModel roleModel) {
        boolean isSuccess = false;

        TRole role = new TRole();
        role.setRolename(roleModel.getRolename());
//        role.setCreateUserId(getLoginUser().getId());
//        role.setCreateTime(new Date());
//        role.setStatus((byte) ERoleStatus.NORMAL.getValue().intValue());
//        role.setFlag((byte) EFlag.NORMAL.getValue().intValue());
        int result = roleMapper.insertSelective(role);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 创建角色
     *
     * @param roleModel {@link RoleModel}
     * @return boolean
     */
    public boolean deleteRole(RoleModel roleModel) {
        boolean isSuccess = false;

        TRole role = new TRole();
//        role.setId(roleModel.getId());
//        role.setFlag((byte) EFlag.DELETE.getValue().intValue());
//        role.setModifyTime(new Date());
//        role.setModifyUserId(getLoginUser().getId());
        int result = roleMapper.updateByPrimaryKeySelective(role);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 获取角色菜单列表
     *
     * @param roleId {@link Integer}
     * @return List
     */
    public List<RoleMenuVO> getRoleMenuList(Integer roleId) {
        RoleMenuData roleMenuData = new RoleMenuData();
        roleMenuData.setRoleId(roleId);
        List<RoleMenuVO> roleMenuList = Lists.newArrayList();

        TMenuExample menuExample = new TMenuExample();
//        menuExample.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        List<TMenu> menuList = menuMapper.selectByExample(menuExample);

        TRoleMenuExample roleMenuExample = new TRoleMenuExample();
//        roleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
        List<TRoleMenu> dbRoleMenuList = roleMenuMapper.selectByExample(roleMenuExample);
        List<Integer> menuIdList = getMenuIdList(dbRoleMenuList);

        Map<Integer, List<TPermission>> menuIdToPermissionListMap = getMenuIdToPermissionListMap();

        TRolePermissionExample rolePermissionExample = new TRolePermissionExample();
//        rolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        List<TRolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);
        List<Integer> permissionIdList = getPermissionIdList(rolePermissionList);

        menuList.forEach(menu -> {
            RoleMenuVO vo = new RoleMenuVO();
            BeanUtils.copyProperties(menu, vo);
//            vo.setMenuId(menu.getId());
//            if (menuIdList.contains(menu.getId())) {
//                vo.setChecked(true);
//            }

            List<PermissionVO> permissionVOList = Lists.newArrayList();
//            List<TPermission> permList = menuIdToPermissionListMap.get(menu.getId());
//            if (Objects.nonNull(permList)) {
//
//                permList.forEach(permission -> {
//                    PermissionVO permVO = new PermissionVO();
//                    BeanUtils.copyProperties(permission, permVO);
//
//                    if (permissionIdList.contains(permission.getId())) {
//                        permVO.setChecked(true);
//                    }
//
//                    permissionVOList.add(permVO);
//                });
//            }

            vo.setPermList(permissionVOList);

            roleMenuList.add(vo);
        });

        return roleMenuList;
    }

    /**
     * 获取某角色的菜单id列表
     *
     * @param roleMenuList {@link List}
     * @return List
     */
    private List<Integer> getMenuIdList(List<TRoleMenu> roleMenuList) {
        List<Integer> menuIdList = Lists.newArrayList();
//        roleMenuList.forEach(roleMenu -> menuIdList.add(roleMenu.getMenuId()));

        return menuIdList;
    }

    /**
     * 获取某角色的权限id列表
     *
     * @param rolePermissionList {@link List}
     * @return List
     */
    private List<Integer> getPermissionIdList(List<TRolePermission> rolePermissionList) {
        List<Integer> permIdList = Lists.newArrayList();
//        rolePermissionList.forEach(perm -> permIdList.add(perm.getPermissionId()));

        return permIdList;
    }

    /**
     * 更新菜单列表及权限列表
     *
     * @param roleMenuModel {@link RoleMenuModel}
     * @return boolean
     */
    //设置事物隔离级别为REPEATABLE-READ 可避免脏读、不可重复读的发生，但是仍能出现幻读。
    //设置事物传播方式为:支持当前事务，如果当前没有事务，就新建一个事务。
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public boolean updateRoleMenu(RoleMenuModel roleMenuModel) {
        boolean isSuccess = false;

        TRoleMenuExample roleMenuExample = new TRoleMenuExample();
//        roleMenuExample.createCriteria().andRoleIdEqualTo(roleMenuModel.getRoleId());
        //物理删除原有菜单
        roleMenuMapper.deleteByExample(roleMenuExample);
        List<TRoleMenu> roleMenuList = Lists.newArrayList();
        List<TRolePermission> rolePermList = Lists.newArrayList();

        for (MenuEntry menu : roleMenuModel.getMenuList()) {
            if (!menu.isChecked()) {
                continue;
            }

            List<PermEntry> permList = menu.getPermList();

            for (PermEntry permEntry : permList) {
                if (!permEntry.isChecked()) {
                    continue;
                }
                TRolePermission rolePermission = new TRolePermission();
//                rolePermission.setRoleId(roleMenuModel.getRoleId());
//                rolePermission.setPermissionId(permEntry.getId());
//                rolePermission.setCreateTime(new Date());
//                rolePermission.setCreateUserId(getLoginUser().getId());
//                rolePermission.setFlag((byte) EFlag.NORMAL.getValue().intValue());
                rolePermList.add(rolePermission);
            }

            TRoleMenu roleMenu = new TRoleMenu();
//            roleMenu.setRoleId(roleMenuModel.getRoleId());
//            roleMenu.setMenuId(menu.getMenuId());
//            roleMenu.setCreateUserId(getLoginUser().getId());
//            roleMenu.setCreateTime(new Date());
//            roleMenu.setFlag((byte) EFlag.NORMAL.getValue().intValue());
            roleMenuList.add(roleMenu);
        }

        //批量插入新菜单
        Integer roleMenuResult = malRoleMapper.insertRoleMenuBatch(roleMenuList);

        //物理删除角色的权限
        TRolePermissionExample rpExample = new TRolePermissionExample();
//        rpExample.createCriteria().andRoleIdEqualTo(roleMenuModel.getRoleId());
        rolePermissionMapper.deleteByExample(rpExample);

        //批量插入权限
        Integer result = malRoleMapper.insertRolePermBatch(rolePermList);
        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    public List<String> getRoleIdListByUserId(Integer userId) {
        List<String> roleList = Lists.newArrayList();

        TUserRoleExample example = new TUserRoleExample();
//        example.createCriteria().andUserIdEqualTo(userId).andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        List<TUserRole> userRoleList = userRoleMapper.selectByExample(example);
        userRoleList.forEach(userRole -> {
            roleList.add(String.valueOf(userRole.getRoleId()));
        });

        return roleList;
    }

    /**
     * 封装菜单权限集合
     * key: menuId
     * value: PermissionList
     *
     * @return Map
     */
    public Map<Integer, List<TPermission>> getMenuIdToPermissionListMap() {
        Map<Integer, List<TPermission>> menuIdToPermissionListMap = Maps.newHashMap();
        TPermissionExample example = new TPermissionExample();
//        example.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        List<TPermission> permissionList = permissionMapper.selectByExample(example);

        permissionList.forEach(perm -> {
            if (menuIdToPermissionListMap.containsKey(perm.getMenuId())) {
                List<TPermission> permList = menuIdToPermissionListMap.get(perm.getMenuId());
                permList.add(perm);
            } else {
                List<TPermission> permList = Lists.newArrayList();
                permList.add(perm);
//                menuIdToPermissionListMap.put(perm.getMenuId(), permList);
            }
        });

        return menuIdToPermissionListMap;
    }
}
