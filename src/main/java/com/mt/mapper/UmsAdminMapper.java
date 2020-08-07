package com.mt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.bean.UmsAdmin;
import com.mt.bean.UmsPermission;
import com.mt.bean.UmsResource;

import java.util.List;

/**
 * Created by 郭俊旺 on 2020/8/7 9:37
 *
 * @author 郭俊旺
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    public List<UmsPermission> getPermissions(Long userId);

    public List<UmsResource> getResource(Long userId);

    public  List<UmsResource> getAllResource();

}
