package com.xxm.service;

import com.xxm.health.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: IntelliJ IDEA spring_security_demo
 * @Description: TODO
 * @Author: Mr Liu
 * @Creed: Talk is cheap,show me the code
 * @CreateDate: 2019-11-03 10:07:27 周日
 * @LastModifyDate:
 * @LastModifyBy:
 * @Version: V1.0
 */
@Component
public class UserService implements UserDetailsService {
    static BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    static Map<String,com.xxm.health.pojo.User>map=new HashMap<String,com.xxm.health.pojo.User>();
    static {
        com.xxm.health.pojo.User user=new com.xxm.health.pojo.User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setGender("1");
        com.xxm.health.pojo.User user1=new com.xxm.health.pojo.User();
        user1.setUsername("zhangsan");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setGender("2");
        map.put(user.getUsername(),user);
        map.put(user1.getUsername(),user1);
    }
    /**
     * 使用username作为登录名，查询当前用户名具有的用户，并返回用户信息封装到UserDetails的对象中
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //使用username从数据库查询数据
        com.xxm.health.pojo.User user = map.get(username);
        //表示当前用户名查不到用户信息，表示用户名输入有误，return null;
        if (user==null){
            return null;
        }
        //密码使用明文
       //String password= "{noop}"+user.getPassword();
        //密码使用BCryptPasswordEncoder
        String password=user.getPassword();
        //封装当前用户具有的角色和权限
        List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//具有ROLE_ADMIN的角色
        list.add(new SimpleGrantedAuthority("add_checkitem"));//具有add_checkitem的权限
        list.add(new SimpleGrantedAuthority("delete_checkitem"));//具有delete_checkitem的权限
        /**
         * User(String username, String password, Collection<? extends GrantedAuthority> authorities)
         * 参数一：用户名
         * 参数二：密码（使用数据查询的密码{noop}admin和页面输入的密码进行比对，比对成功，可以登录；如果比对不成功，此时登录不成功，抛出异常org.springframework.security.authentication.BadCredentialsException: Bad credentials）
         * 参数三：集合，存放当前用户具有的角色和权限
         */
        UserDetails userDetails=new org.springframework.security.core.userdetails.User(user.getUsername(),password,list);
        return userDetails;
    }
}
