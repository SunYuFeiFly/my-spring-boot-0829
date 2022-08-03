package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;

/**
 * @author syf_12138
 * @Description 部门服务接口
 * @create 2022/8/3 11:03
 */

public interface DeptService {

    Department getDeptById(Integer id);
}
