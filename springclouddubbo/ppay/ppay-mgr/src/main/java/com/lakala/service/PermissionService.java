package com.lakala.service;

import com.lakala.common.constant.OrderByConsts;
import com.lakala.common.constant.SysConsts;
import com.lakala.common.util.ToolUtils;
import com.lakala.dao.gen.mapper.TPermissionMapper;
import com.lakala.dao.gen.model.TPermission;
import com.lakala.dao.gen.model.TPermissionExample;
import com.lakala.dao.manual.mapper.MalPermissionMapper;
import com.lakala.model.input.PermissionModel;
import com.lakala.model.output.PermissionData;
import com.lakala.model.output.PermissionVO;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by user on 2017/4/7.
 */
@Transactional
@Service
public class PermissionService extends BaseService {

    @Autowired
    TPermissionMapper permissionMapper;
    @Autowired
    MalPermissionMapper malPermissionMapper;

    /**
     * 查询权限列表
     *
     * @param permissionModel {@link PermissionModel}
     * @return PermissionData
     */
    public PermissionData permissionData(PermissionModel permissionModel) {

        List<PermissionVO> permissionVOList = Lists.newArrayList();

        TPermissionExample permExample = new TPermissionExample();
//        permExample.createCriteria().andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
//        Integer begin = (permissionModel.getPageNo() - SysConsts.ONE) * permissionModel.getPageSize();
//        Page page = new Page(begin, permissionModel.getPageSize());
//        permExample.setPage(page);
        permExample.setOrderByClause(OrderByConsts.ORDER_BY_ID);
        List<TPermission> permList = permissionMapper.selectByExample(permExample);

        long totalCount = permissionMapper.countByExample(permExample);
        PermissionData permData = new PermissionData();
        permData.setTotalCount((int) totalCount);
        Integer totalPage = (int) Math.round(Math.ceil((double) totalCount / permissionModel.getPageSize()));
        permData.setTotalPage(totalPage);

        permList.forEach(perm -> {
            PermissionVO vo = getPermissionVO(perm);
            permissionVOList.add(vo);
        });

        permData.setPermissionList(permissionVOList);

        return permData;
    }

    /**
     * 封装vo
     *
     * @param perm {@link TPermission}
     * @return PermissionVO
     */
    private PermissionVO getPermissionVO(TPermission perm) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(perm, vo);

        if (Objects.isNull(perm.getCreateTime())) {
            vo.setCreateTime(StringUtils.EMPTY);
        } else {
            vo.setCreateTime(ToolUtils.DATEFORMATTER.format(perm.getCreateTime()));
        }

        return vo;
    }

    /**
     * 根据用户id获取权限
     *
     * @param userId {@link Integer}
     * @return List
     */
    public List<String> getPermissionListByUserId(Integer userId) {
        List<String> permissionList = malPermissionMapper.getPermissionListByUserId(userId);
        return permissionList;
    }

    /**
     * 创建权限
     *
     * @param permissionModel {@link TPermission}Model
     * @return boolean
     */
    public boolean createPermission(PermissionModel permissionModel) {
        boolean isSuccess = false;

        TPermission permission = new TPermission();
        BeanUtils.copyProperties(permissionModel, permission);
//        permission.setCreateUserId(getLoginUser().getId());
//        permission.setCreateTime(new Date());
//        permission.setFlag((byte) EFlag.NORMAL.getValue().intValue());
        int result = permissionMapper.insert(permission);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 更新权限
     *
     * @param permissionModel {@link PermissionModel}
     * @return boolean
     */
    public boolean updatePermission(PermissionModel permissionModel) {
        boolean isSuccess = false;

        TPermission permission = new TPermission();
        BeanUtils.copyProperties(permissionModel, permission);
//        permission.setModifyUserId(getLoginUser().getId());
        permission.setModifyTime(new Date());
        int result = permissionMapper.updateByPrimaryKeySelective(permission);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }

    /**
     * 删除权限
     *
     * @param permissionModel {@link PermissionModel}
     * @return boolean
     */
    public boolean deletePermission(PermissionModel permissionModel) {
        boolean isSuccess = false;

        TPermission permission = new TPermission();
        BeanUtils.copyProperties(permissionModel, permission);
//        permission.setFlag((byte) EFlag.DELETE.getValue().intValue());
//        permission.setModifyUserId(getLoginUser().getId());
        permission.setModifyTime(new Date());
        int result = permissionMapper.updateByPrimaryKeySelective(permission);

        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }
}

