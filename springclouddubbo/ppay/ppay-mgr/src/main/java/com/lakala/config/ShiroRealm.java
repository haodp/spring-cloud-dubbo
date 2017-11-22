package com.lakala.config;

import com.lakala.dao.gen.model.TUser;
import com.lakala.model.output.UserInfo;
import com.lakala.service.PermissionService;
import com.lakala.service.RoleService;
import com.lakala.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 验证用户登录及授权
 *
 * @author user
 */
@Component("userRealm")
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    public ShiroRealm() {
        setName("ShiroRealm");
        // 密码验证方式为Md5Hash
        setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
    }

    /**
     * to login
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        AuthenticationInfo authenticationInfo = null;
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        TUser user = userService.getUserByUsername(String.valueOf(token.getPrincipal()));
        if (Objects.nonNull(user)) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);
            userInfo.setGender(user.getGender().intValue());
            //密码验证
            ByteSource credentialsSalt = ByteSource.Util.bytes(String.valueOf(user.getSaltVal()));
            authenticationInfo = new SimpleAuthenticationInfo(userInfo, user.getPassWord(), credentialsSalt, getName());
            //结束
        } else {
            throw new UnknownAccountException();
        }

        return authenticationInfo;
    }

    /**
     * to init roles and permissions
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = null;
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();

        if (Objects.nonNull(userInfo)) {
            authorizationInfo = new SimpleAuthorizationInfo();
            List<String> roles = roleService.getRoleIdListByUserId(userInfo.getId());
            authorizationInfo.addRoles(roles);

            List<String> permList = permissionService.getPermissionListByUserId(userInfo.getId());
            authorizationInfo.addStringPermissions(permList);
        }

        return authorizationInfo;
    }
}