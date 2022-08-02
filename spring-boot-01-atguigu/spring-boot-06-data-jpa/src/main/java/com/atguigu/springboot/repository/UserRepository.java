package com.atguigu.springboot.repository;


import com.atguigu.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author syf_12138
 * @Description 继承JpaRepository来完成对数据库的操作
 * @create 2022/8/2 16:12
 */

public interface UserRepository extends JpaRepository<User,Integer> {

}
