package com.atguigu.springboot.entity;


import javax.persistence.*;

/**
 * @author syf_12138
 * @Description 实体类
 *          @Entity //告诉JPA这是一个实体类（和数据表映射的类）
 *          @Table(name = "tbl_user") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
 *
 * @create 2022/8/2 16:08
 */

@Entity
@Table(name = "tbl_user")
public class User {

    /** 这是一个主键、自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 这是和数据表对应的一个列 */
    @Column(name = "last_name",length = 50)
    private String lastName;

    /** 省略默认列名就是属性名 */
    @Column
    private String email;

}
