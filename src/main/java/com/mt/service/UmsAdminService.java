package com.mt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mt.bean.UmsAdmin;
import com.mt.bean.UmsPermission;
import com.mt.bean.UmsResource;
import com.mt.config.JwtTokenFilter;
import com.mt.mapper.UmsAdminMapper;
import com.mt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 郭俊旺 on 2020/8/7 10:02
 *
 * @author 郭俊旺
 */
@Service
public class UmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return {@link UmsAdmin}
     */
    public UmsAdmin findUserByUserName(String username){

        QueryWrapper qw = new QueryWrapper();
        qw.eq("username",username);

        return umsAdminMapper.selectOne(qw);
    }

    /**
     * 通过用户id获取用户的权限
     * @param userId 用户id
     * @return {@link List<UmsPermission>}
     */
    public List<UmsPermission> getUserPermission(Long userId){
        return umsAdminMapper.getPermissions(userId);
    }

    /**
     * 通过用户id 获取用户的resource
     * @param userId 用户id
     * @return {@link List<UmsResource>}
     */
    public List<UmsResource> getUserResource(Long userId){
        return umsAdminMapper.getResource(userId);
    }


    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token  or null (登陆失败)
     */
    public String login(String username, String password) {

        UserDetails userDetails = null;
        try {
             userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        //如果密码匹配 生成token
        if(passwordEncoder.matches(password,userDetails.getPassword())){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
             return jwtTokenUtil.generateToken(userDetails);
        }

        return null;
    }
}
