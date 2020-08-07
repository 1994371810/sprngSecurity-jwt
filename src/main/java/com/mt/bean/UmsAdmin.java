package com.mt.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by 郭俊旺 on 2020/8/6 10:39
 *
 * @author 郭俊旺
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UmsAdmin {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String icon;
    private String email;
    private String nick_name;
    private String note;
    private Date createTime;
    private Date loginTime;
    private int status;

}
