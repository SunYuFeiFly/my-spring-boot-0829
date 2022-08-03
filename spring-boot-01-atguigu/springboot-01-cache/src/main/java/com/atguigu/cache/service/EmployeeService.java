package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;

/**
 * @author syf_12138
 * @Description 员工服务接口
 * @create 2022/8/3 11:09
 */

public interface EmployeeService {

    Employee getEmp(Integer id);

    Employee updateEmp(Employee employee);

    void deleteEmp(Integer id);

    Employee getEmpByLastName(String lastName);
}
