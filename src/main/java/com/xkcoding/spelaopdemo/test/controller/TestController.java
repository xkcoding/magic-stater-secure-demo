package com.xkcoding.spelaopdemo.test.controller;

import cn.hutool.core.collection.CollUtil;
import com.xkcoding.spelaopdemo.annotation.CurrentUser;
import com.xkcoding.spelaopdemo.annotation.Secure;
import com.xkcoding.spelaopdemo.model.SecureUser;
import com.xkcoding.spelaopdemo.util.JwtUtil;
import com.xkcoding.spelaopdemo.util.SecureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/18 15:30
 */
@Secure("permitAll()")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestController {
    private final SecureUtil secureUtil;
    private final JwtUtil jwtUtil;

    @Secure("anon()")
    @GetMapping("/login")
    public String login() {
        SecureUser secureUser = new SecureUser();
        secureUser.setId(1L);
        secureUser.setUsername("xkcoding");
        secureUser.setPermissions(CollUtil.newHashSet("user::list::add", "user::list::delete"));
        return jwtUtil.generateToken(secureUser);
    }

    @Secure("anon()")
    @GetMapping("/logout")
    public String logout() {
        return "logout success!";
    }

    @GetMapping("/class")
    public String testClass() {
        return "class";
    }

    @Secure("denyAll()")
    @GetMapping("/method")
    public String method() {
        return "method";
    }

    @Secure("@test.hasPermission(#permission)")
    @GetMapping("/spring")
    public String springBean(String permission) {
        return "spring bean";
    }

    @Secure("hasLogin()")
    @GetMapping("/hasLogin")
    public String testFun() {
        return "hasLogin";
    }

    @Secure("hasPermission('user::list::add')")
    @GetMapping("/user/add")
    public String testPermissionAdd() {
        return "has [user::list::add] permission";
    }

    @Secure("hasPermission('user::list::*')")
    @GetMapping("/user/select")
    public String testPermissionSelect() {
        return "has " + secureUtil.getCurrentUser().getPermissions() + " permission";
    }

    @Secure("hasLogin()")
    @GetMapping("/user")
    public SecureUser testGetUserInfo(@CurrentUser SecureUser user) {
        return user;
    }

    @GetMapping("/interceptor")
    public SecureUser interceptor(@CurrentUser SecureUser user) {
        return user;
    }
}
