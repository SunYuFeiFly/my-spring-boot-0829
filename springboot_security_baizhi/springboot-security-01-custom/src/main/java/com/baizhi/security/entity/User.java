package com.baizhi.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author syf_12138
 * @Description 用户实体类（此处不能使用Lombok的@Data注解，因为默认产生的get方法与父类里面is方法冲突，如isAccountNonExpired与getAccountNonExpired均被看做是对accountNonExpired对象的获取工作）
 * @create 2022/8/10 17:31
 */

public class User implements UserDetails {

    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 是否可用 */
    private Boolean enabled;
    /** 账号是否过期 */
    private Boolean accountNonExpired;
    /** 账号是否被锁定 */
    private Boolean accountNonLocked;
    /** 凭证（密码）是否过期 */
    private Boolean credentialsNonExpired;
    /** 关系属性，用来存储当前用户所有角色信息 */
    private List<Role> roles = new ArrayList<>();

    /**
     * 返回权限信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(simpleGrantedAuthority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
