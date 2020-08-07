package com.mt.controller;

import com.mt.bean.UmsAdmin;
import com.mt.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 郭俊旺 on 2020/8/7 15:20
 *
 * @author 郭俊旺
 */
@RestController
public class UserController {
    @Autowired
    private UmsAdminService umsAdminService;

    @GetMapping("/login")
    public ResponseEntity responseEntity(String username,String password){

        String token = umsAdminService.login(username, password);

        if(token==null){
            ResponseEntity responseEntity = new ResponseEntity("登陆失败",HttpStatus.UNAUTHORIZED);
            return responseEntity;
        }
        return new ResponseEntity("登陆成功==>"+token,HttpStatus.OK);
    }


    @GetMapping("/test")
    @PostAuthorize("hasAuthority('3:商品属性管2理')")
    public  String test(){
        return "您以登陆!";
    }

}
