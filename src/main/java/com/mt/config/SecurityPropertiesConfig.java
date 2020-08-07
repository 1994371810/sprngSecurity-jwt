package com.mt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 郭俊旺 on 2020/8/7 14:07
 * 配置允许访问的地址
 * @author 郭俊旺
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityPropertiesConfig {

    /**忽略Url权限校验*/
    private Set<String> ignoreUrl = new HashSet();

    /**token的请求头名称*/
    private String tokenHeaderName;

    /**生成token的密钥*/
    private String secretKey;

    /**token的过期时间 单位秒*/
    private Long expiration;

}

