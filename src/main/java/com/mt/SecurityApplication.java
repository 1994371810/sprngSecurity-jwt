package com.mt;

import com.mt.config.SecurityPropertiesConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by 郭俊旺 on 2020/8/7 9:12
 *
 * @author 郭俊旺
 */
@SpringBootApplication
@EnableWebSecurity
@MapperScan("com.mt.mapper")
@EnableConfigurationProperties({SecurityPropertiesConfig.class})
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class);
    }

}
