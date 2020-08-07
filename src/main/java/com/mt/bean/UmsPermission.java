package com.mt.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by 郭俊旺 on 2020/8/6 10:43
 *
 * @author 郭俊旺
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UmsPermission {
    private Long id;
    private Long pid;
    private String name;
    private String value;
    private String icon;
    private Integer type;
    private String uri;
    private int status;
    private Date createTime;
    private Integer sort;
}
