package com.mt.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by 郭俊旺 on 2020/8/6 18:57
 *
 * @author 郭俊旺
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UmsResource {
    @TableId
    private Long id;
    private Date createTime;
    private String name;
    private String url;
    private String description;
    private Long category_id;
}
