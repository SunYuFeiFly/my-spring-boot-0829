package com.atguigu.springboot.mapper;


import com.atguigu.springboot.bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author syf_12138
 * @Description mapper操作，映射版本（可在接口上@Mapper或在主启动类上@MapperScan全局扫描）
 * @create 2022/8/2 15:15
 */

@Mapper
public interface EmployeeMapper {

    public Employee getEmpById(@Param("id") Integer id);

    public void insertEmp(@Param("employee") Employee employee);
}
