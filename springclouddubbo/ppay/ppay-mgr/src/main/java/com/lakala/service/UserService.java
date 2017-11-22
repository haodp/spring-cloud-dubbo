package com.lakala.service;

import com.lakala.common.constant.EFlag;
import com.lakala.common.constant.EGender;
import com.lakala.common.constant.EUserStatus;
import com.lakala.common.constant.SysConsts;
import com.lakala.common.util.EncryptUtils;
import com.lakala.dao.gen.mapper.TUserMapper;
import com.lakala.dao.gen.mapper.TUserRoleMapper;
import com.lakala.dao.gen.model.TUser;
import com.lakala.dao.gen.model.TUserExample;
import com.lakala.dao.gen.model.TUserRole;
import com.lakala.dao.gen.model.TUserRoleExample;
import com.lakala.dao.manual.mapper.MalUserMapper;
import com.lakala.dao.manual.model.MalUser;
import com.lakala.model.input.UserModel;
import com.lakala.model.output.UserData;
import com.lakala.model.output.UserVO;
import com.lakala.model.query.UserQO;
import com.google.common.collect.Lists;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by user on 2017/3/24.
 */
@Transactional
@Service
public class UserService extends BaseService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TUserRoleMapper userRoleMapper;

    @Autowired
    private MalUserMapper malUserMapper;

    /**
     * 根据用户名获取用户信息
     *
     * @param username {@link String}
     * @return User
     */
    public TUser getUserByUsername(String username) {
        TUser user = null;
        TUserExample example = new TUserExample();
//        example.createCriteria().andUsernameEqualTo(username).andFlagEqualTo((byte) EFlag.NORMAL.getValue().intValue());
        List<TUser> userList = userMapper.selectByExample(example);

        if (!userList.isEmpty()) {
            user = userList.get(SysConsts.ZERO);
        }

        return user;
    }

    @Deprecated
    public boolean checkPassword(TUser user, String password) {
        boolean isValidate = false;
//        String tempPassword = EncryptUtils.encodeMD5WithSalt(password, user.getSaltVal());
//        if (Objects.equals(tempPassword, user.getPassWord())) {
//            isValidate = true;
//        }

        return isValidate;
    }

    public List<String> getPermissionList(Integer userId) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return UserData
     */
    public UserData getUserData(UserModel userModel) {
        UserData userData = new UserData();
        getUserList(userModel, userData);

        return userData;
    }

    /**
     * 查询用户
     *
     * @return void
     */
    public void getUserList(UserModel userModel, UserData userData) {
        List<UserVO> userVOList = Lists.newArrayList();
        UserQO userQO = new UserQO();
        BeanUtils.copyProperties(userModel, userQO);
        Integer userTotalCount = malUserMapper.getUserTotalCount(userQO);
        userData.setTotalCount(userTotalCount);
        //分页
        if (userModel.getPageNo() < SysConsts.ONE) {
            userModel.setPageNo(SysConsts.ONE);
        }

        Integer begin = (userModel.getPageNo() - SysConsts.ONE) * userModel.getPageSize();
        userQO.setBegin(begin);
        List<MalUser> userList = malUserMapper.getUserList(userQO);

        userList.forEach(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
//            vo.setUserGender(EGender.fromValue(user.getGender()).getName());
//            vo.setUserStatus(EUserStatus.fromValue(user.getStatus()).getName());
//            vo.setGenderId(user.getGender());
            userVOList.add(vo);
        });

        userData.setUserList(userVOList);
        Integer totalPage = (int) Math.round(Math.ceil((double) userTotalCount / userModel.getPageSize()));
        userData.setTotalPage(totalPage);
    }

    /**
     * 创建用户
     *
     * @param userModel {@link UserModel}
     * @return boolean
     */
    public boolean createUser(UserModel userModel) {
        boolean isSuccess = false;
        TUser user = new TUser();
        BeanUtils.copyProperties(userModel, user);
        int salt = EncryptUtils.getSalt();
//        user.setSaltVal(salt);
//        user.setCreateTime(new Date());
//        user.setCreateUserId(getLoginUser().getId());
//        user.setFlag((byte) EFlag.NORMAL.getValue().intValue());
//        user.setStatus((byte) EUserStatus.NORMAL.getValue().intValue());
//        user.setGender((byte) EGender.fromValue(userModel.getGender()).getValue().intValue());
        user.setPassWord(EncryptUtils.encodeMD5WithSalt(userModel.getPassword(), salt));
        int result = userMapper.insertSelective(user);

        if (result > SysConsts.ZERO) {
            TUserRole userRole = new TUserRole();
//            userRole.setUserId(user.getUuid());
//            userRole.setRoleId(userModel.getRole());
//            userRole.setCreateTime(new Date());
//            userRole.setFlag((byte) EFlag.NORMAL.getValue().intValue());
//            userRole.setCreateUserId(1);//TODO
            int urResult = userRoleMapper.insert(userRole);

            if (urResult > SysConsts.ZERO) {
                isSuccess = true;
            }

        }

        return isSuccess;
    }

    /**
     * 更新用户
     *
     * @param userModel {@link UserModel}
     * @return boolean
     */
    public boolean updateUser(UserModel userModel) {
        boolean isSuccess = false;
        TUser user = new TUser();
        BeanUtils.copyProperties(userModel, user);
//        user.setGender((byte) EGender.fromValue(userModel.getGender()).getValue().intValue());
//        user.setModifyTime(new Date());
//        user.setModifyUserId(getLoginUser().getId());
        int result = userMapper.updateByPrimaryKeySelective(user);

        if (result > SysConsts.ZERO) {
            TUserRoleExample example = new TUserRoleExample();
//            example.createCriteria().andUserIdEqualTo(user.getId());
            List<TUserRole> userRoleList = userRoleMapper.selectByExample(example);

            if (!userRoleList.isEmpty()) {
                TUserRole userRole = new TUserRole();
//                userRole.setId(userRoleList.get(SysConsts.ZERO).getId());
//                userRole.setRoleId(userModel.getRole());
                int urResult = userRoleMapper.updateByPrimaryKeySelective(userRole);
                if (urResult > SysConsts.ZERO) {
                    isSuccess = true;
                }
            }
        }

        return isSuccess;
    }

    /**
     * 删除用户
     *
     * @param userModel {@link UserModel}
     * @return boolean
     */
    public boolean deleteUser(UserModel userModel) {
        boolean isSuccess = false;
        TUser user = new TUser();
//        user.setId(userModel.getId());
        user.setModifyTime(new Date());
//        user.setModifyUserId(getLoginUser().getId());
//        user.setFlag((byte) EFlag.DELETE.getValue().intValue());
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result > SysConsts.ZERO) {
            isSuccess = true;
        }

        return isSuccess;
    }
}
