package com.baizhi.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author LiJian
 * @Date 2022/7/27 0:54
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permission")
public class Permission {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String url;
}
