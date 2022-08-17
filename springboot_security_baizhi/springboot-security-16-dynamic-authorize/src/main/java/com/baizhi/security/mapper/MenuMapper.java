package com.baizhi.security.mapper;

import com.baizhi.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author syf_12138
 * @Description 按钮（请求路径）映射接口类
 * @create 2022/8/17 15:02
 */

@Mapper
public interface MenuMapper {

    List<Menu> getAllMenu();
}
