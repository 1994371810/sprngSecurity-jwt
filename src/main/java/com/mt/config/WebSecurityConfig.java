package com.mt.config;

import com.mt.config.userDetail.AdminUserDetailsService;
import com.mt.config.exception.RestAuthenticationEntryPoint;
import com.mt.config.exception.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by 郭俊旺 on 2020/8/7 10:22
 *
 * @author 郭俊旺
 */
@Configuration
//允许通过注解的方式控制权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminUserDetailsService userDetailsService;
    @Autowired
    private SecurityPropertiesConfig ignorePathConfig;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private RestAuthenticationEntryPoint  restAuthenticationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许访问 的地址
        String[] allowAccessPath = ignorePathConfig.getIgnoreUrl().toArray(new String[ignorePathConfig.getIgnoreUrl().size()]);
        System.out.println("===>"+allowAccessPath);
        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(allowAccessPath).permitAll()//允许配置的请求访问
                .antMatchers(HttpMethod.OPTIONS).permitAll()//允许跨域验证请求访问
                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



}
