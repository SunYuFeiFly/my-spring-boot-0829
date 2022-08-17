package com.baizhi.security.security.metasecourse;

import com.baizhi.security.entity.Menu;
import com.baizhi.security.entity.Role;
import com.baizhi.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author syf_12138
 * @Description 自定义动态资源权限获取服务类（此部分我们只是把访问路径和需要的角色对照信息获取，认证的地方还是单独做）
 * @create 2022/8/17 15:26
 */

@Component
public class CustomerSecurityMetaSource implements FilterInvocationSecurityMetadataSource {

    private final MenuService menuService;

    @Autowired
    public CustomerSecurityMetaSource(MenuService menuService) {
        this.menuService = menuService;
    }

    /** 专门用来比照路径 */
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 自定义动态资源权限元数据信息（访问路径与角色对照表）
     * object:当前的请求对象
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取当前请求路径
        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        // 查询所有菜单
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getPattern(), requestURI)) {
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                System.out.println("具有角色：" + SecurityConfig.createList(roles).toArray().toString());
                return SecurityConfig.createList(roles);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
