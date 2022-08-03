package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author syf_12138
 * @Description 员工服务接口
 * @create 2022/8/3 11:09
 */

public interface EmployeeService {

    Employee getEmp(@Param("id") Integer id);

    Employee updateEmp(Employee employee);

    void deleteEmp(@Param("id") Integer id);

    Employee getEmpByLastName(String lastName);
}
