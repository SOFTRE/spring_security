package com.xxm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: IntelliJ IDEA spring_security_demo
 * @Description: TODO
 * @Author: Mr Liu
 * @Creed: Talk is cheap,show me the code
 * @CreateDate: 2019-11-03 11:17:57 周日
 * @LastModifyDate:
 * @LastModifyBy:
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @RequestMapping(value = "/add")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public String add(){
        System.out.println("add...");
        return "success";
    }
    @RequestMapping(value = "/delete")
    @PreAuthorize(value = "hasAuthority('delete_checkitem')")
    public String delete(){
        System.out.println("delete...");
        return "success";
    }
    @RequestMapping(value = "/update")
    @PreAuthorize(value = "hasAuthority('update_checkitem')")
    public String update(){
        System.out.println("update...");
        return "success";
    }
}
