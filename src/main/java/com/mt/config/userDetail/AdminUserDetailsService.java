package com.mt.config.userDetail;

import cn.hutool.core.util.StrUtil;
import com.mt.bean.UmsAdmin;
import com.mt.bean.UmsPermission;
import com.mt.bean.UmsResource;
import com.mt.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 郭俊旺 on 2020/8/7 9:58
 *
 * @author 郭俊旺
 */
@Component
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private UmsAdminService umsAdminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询用户信息
        UmsAdmin umsAdmin = umsAdminService.findUserByUserName(username);

        //如果查询不到 抛出异常
        if(umsAdmin == null || StrUtil.isBlank( umsAdmin.getPassword() ) ){
            throw new UsernameNotFoundException("用户不存在!");
        }

        //查询用户的权限信息
        List<UmsPermission> userPermission = umsAdminService.getUserPermission(umsAdmin.getId());

        //查询用户的resource信息
        List<UmsResource> userResource = umsAdminService.getUserResource(umsAdmin.getId());

        //封装 adminUserDetail 返回
        AdminUserDetail userDetail = new AdminUserDetail(umsAdmin,userResource,userPermission);

        return userDetail;
    }


}
