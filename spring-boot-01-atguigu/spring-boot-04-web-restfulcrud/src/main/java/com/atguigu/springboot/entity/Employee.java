package com.atguigu.springboot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author syf_12138
 * @Description 员工实体类
 * @create 2022/8/2 09:55
 */

@Data
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String lastName;
    private String email;
    // 1 male, 0 female
    private Integer gender;
    private Department department;
    private Date birth;

    public Employee(Integer id, String lastName, String email, Integer gender,
                    Department department) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.department = department;
        this.birth = new Date();
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", department=" + department +
                ", birth=" + birth +
                '}';
    }
}
