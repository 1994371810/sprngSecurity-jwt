package com.mt.config;

import cn.hutool.core.util.StrUtil;
import com.mt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.sql.Struct;

/**
 * Created by 郭俊旺 on 2020/8/7 14:22
 *
 * @author 郭俊旺
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private SecurityPropertiesConfig config;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        //获取请求头
        String token = request.getHeader(config.getTokenHeaderName());
        //如果请求头
        if(StrUtil.isBlank(token)){
            chain.doFilter(request,response);
            return;
        }

        String username = jwtTokenUtil.getUserNameFromToken(token);

        if(StrUtil.isBlank(username)){
            chain.doFilter(request,response);
            return;
        }



        SecurityContext context = SecurityContextHolder.getContext();

        //如果还没认证
        if( context.getAuthentication() == null ){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtTokenUtil.validateToken(token,userDetails)){
                //如果token有效并且 用户信息正常 放入 authentication 放行
                context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities() ));
                chain.doFilter(request,response);
                return;
            }
            chain.doFilter(request,response);
        }
    }

}
