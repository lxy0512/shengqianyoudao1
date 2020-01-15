package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.*;
import cn.qiandao.shengqianyoudao.service.impl.LoginServiceImpl;
import cn.qiandao.shengqianyoudao.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@RestController
    @RequestMapping("/user")
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoServiceImpl usi;
    private LoginServiceImpl lsi;

    /**
     * 修改个人信息
     */
    @RequestMapping("/change")
    @ResponseBody
    public String changeUserInfo(@RequestBody User user){
        return usi.changeUserInfo(user);
    }

    /**
     * 查询我的关注
     */
    @RequestMapping("/attention/{usernumber}")
    @ResponseBody
    public ArrayList<Breviary> attention1(@PathVariable("usernumber")String usernumber){
        System.out.println(usernumber+"***");
        return usi.attention(usernumber,1);
    }

    /**
     * 查询关注我的
     */
    @RequestMapping("/befocused/{usernumber}")
    @ResponseBody
    public ArrayList<Breviary> attention2(@PathVariable("usernumber")String usernumber){
        return usi.attention(usernumber,2);
    }

    /**
     *
     */
    @RequestMapping("/codenull/{usernumber}")
    @ResponseBody
    public Breviary invitationCodeNull(@PathVariable("usernumber")String usernumber){
        return usi.invitationCodeNull(usernumber);
    }

    /**
     * 填写邀请码
     */
    @RequestMapping("/writeicode/{usernumber}/{code}")
    @ResponseBody
    public String invitationCode(@PathVariable("usernumber")String usernumber,@PathVariable("code")String code){
        return usi.invitationCode(usernumber,code);
    }

    /**
     * 后台查看用户列表
     */
    @RequestMapping("/getinfo")
    @ResponseBody
    public List<Breviary> getAllUserInfo() {
        return usi.getAllUserInfo();
    }

    /**
     * 查看用户信息
     */
    @RequestMapping("/getinfo/{usernumber}")
    @ResponseBody
    public User getUserInfo(@PathVariable("usernumber")String usernumber){
        return usi.getuserinfo(usernumber);
    }


    /**
     * 后台删除用户信息
     */
    @RequestMapping("/deleteUser/{usernumber}")
    @ResponseBody
    public String deleteUser(@PathVariable("usernumber")String usernumber){
        return usi.deleteUser(usernumber);
    }
}
