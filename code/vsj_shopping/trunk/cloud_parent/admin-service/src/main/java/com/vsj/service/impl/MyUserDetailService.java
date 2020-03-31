package com.vsj.service.impl;

import com.vsj.dao.SysPermissionDAO;
import com.vsj.dao.UserDAO;
import com.vsj.model.VsjSysPermission;
import com.vsj.model.VsjSysRole;
import com.vsj.model.VsjSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 〈自定义UserDetailService〉
 * 自定义认证逻辑
 * @author wangmx
 * @since 1.0.0
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SysPermissionDAO sysPermissionDAO;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        VsjSysUser sysUser = userDAO.findByMemberName(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException(userName);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;
        for (VsjSysRole role : sysUser.getRoles()) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            Set<VsjSysPermission> sysPermissions=sysPermissionDAO.getPermissionsByRoleId(role.getId());
            role.setPermission(sysPermissions);
            for (VsjSysPermission permission : role.getPermission()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getCode());
                grantedAuthorities.add(authority);
            }
        }
        User user = new User(sysUser.getAccount(), sysUser.getPswd(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return user;
    }

}

