package com.mt.config.userDetail;

import cn.hutool.core.util.StrUtil;
import com.mt.bean.UmsAdmin;
import com.mt.bean.UmsPermission;
import com.mt.bean.UmsResource;
import lombok.AllArgsConstructor;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 郭俊旺 on 2020/8/7 9:46
 *
 * @author 郭俊旺
 */
@AllArgsConstructor
public class AdminUserDetail implements UserDetails {

    private UmsAdmin umsAdmin;
    private List<UmsResource> umsResources;
    private List<UmsPermission> umsPermissions;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //resoruce的权限
        Set<GrantedAuthority> resourceAuthorities = umsResources.stream().filter(o -> o.getId() != null && StrUtil.isNotBlank(o.getName()))
                .map(o -> new SimpleGrantedAuthority( o.getId() + ":" + o.getName() ) )
                .collect(Collectors.toSet());

        //permission的权限
        Set<GrantedAuthority> permissionAuthorities = umsPermissions.stream().filter(o -> StrUtil.isNotBlank(o.getValue()))
                .map(o -> new SimpleGrantedAuthority( o.getValue() )).collect(Collectors.toSet());

        //两个权限合并
        resourceAuthorities.addAll(permissionAuthorities);
        System.out.println("用户拥有的权限==>"+resourceAuthorities);
        //返回
        return resourceAuthorities;
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus()==1;
    }

}
