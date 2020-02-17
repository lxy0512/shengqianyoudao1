package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Userinfo;
import cn.qiandao.shengqianyoudao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxy
 * @date 2020/2/2 0002 12:13
 **/
@RestController
public class TestController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public Userinfo getUser(String id){
        return userService.findById(id);
    }
}
