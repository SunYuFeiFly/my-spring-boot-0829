package com.atguigu.cache.service.impl;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import com.atguigu.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author syf_12138
 * @Description 部门服务接口实现类
 * @create 2022/8/3 11:05
 */

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getDeptById(Integer id) {
        Department department = departmentMapper.getDeptById(id);
        return department;
    }
}
