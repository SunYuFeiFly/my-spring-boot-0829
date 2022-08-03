package com.atguigu.cache.service.impl;


import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import com.atguigu.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author syf_12138
 * @Description 员工服务接口实现类
 * @create 2022/8/3 11:10
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.getEmpById(id);
        return employee;
    }

    @Override
    public Employee updateEmp(Employee employee) {
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @Override
    public void deleteEmp(Integer id) {
        employeeMapper.deleteEmpById(id);
    }

    @Override
    public Employee getEmpByLastName(String lastName) {
        Employee employee = employeeMapper.getEmpByLastName(lastName);
        return employee;
    }
}
