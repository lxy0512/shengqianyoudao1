package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.User;
import cn.qiandao.shengqianyoudao.service.impl.LoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author wt
 * @data
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImpl lsi;

    //cookie登录
    @CrossOrigin
    @RequestMapping("/cv")
    @ResponseBody
    public User cvLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进行cookie登录***");
        return lsi.validationCookie(request);
    }

    //密码登录
    @CrossOrigin
//    @RequiresPermissions("qx05")
    @RequestMapping("/ptl/{name}/{pwd}")
    @ResponseBody
    public User paaswordToLogin(@PathVariable("name") String name, @PathVariable("pwd") String pwd, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("进行密码登录***");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("js03")) {
            return lsi.ptlLogin(name, pwd, response, request);
        } else {
            System.out.println(123);
            return null;
        }

    }

    //验证码登录
    @CrossOrigin
    @RequestMapping("/vcl/{phone}/{vc}")
    @ResponseBody
    public User verificationCodeLogin(@PathVariable("phone") String phone, @PathVariable("vc") String vc) {
        User u = new User();
        System.out.println("进行验证码登录***");
        if (lsi.verifyPhoneNumber(phone)) {
            System.out.println("搜索到此账户***");
            if (lsi.verificationCode(phone, vc)) {
                System.out.println("账户和验证码比对成功***");
                u = lsi.getUserInfo(phone);
            }
        }
        System.out.println("验证码登录成功***");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("js03")) {
            return u;
        } else {
            return null;
        }
    }

    //忘记密码的验证码
    @CrossOrigin
    @RequestMapping("/ftpvc/{phone}/{fvc}")
    @ResponseBody
    public String forgetThePassword(@PathVariable("phone") String sphone, @PathVariable("fvc") String fvc) {
        System.out.println("开始验证忘记密码");
        if (lsi.verificationCode(sphone, fvc)) {
            return "修改密码";
        } else {
            return "验证错误";
        }

    }

    //获取验证码
    @CrossOrigin
    @RequestMapping("/gc/{phone}/{number}")
    @ResponseBody
    public void getCode(@PathVariable("phone") String phone, @PathVariable("number") String number) {
        System.out.println("准备发送验证码***");
        lsi.getcode(phone, Integer.valueOf(number));
        System.out.println("验证码已发送***");
    }

    //注册
    @CrossOrigin
    @RequestMapping("/vcr/{phone}/{vc}/{rpwd}/{icode}")
    @ResponseBody
    public String verificationCodeRegister(@PathVariable("phone") String phone, @PathVariable("vc") String vc, @PathVariable("rpwd") String rpwd, @PathVariable("icode") String icode, HttpServletResponse response) {
        Boolean b = lsi.verificationCode(phone, vc);
        if (b == null) {
            System.out.println("注册验证码错误***");
            b = false;
        }
        if (b) {
            System.out.println("获取用户信息***");
            return lsi.saveRegisterUser(phone, rpwd, icode, response);
        } else {
            return "注册失败";
        }

    }

    @CrossOrigin
    @RequestMapping("/vcr/{phone}/{vc}/{rpwd}/")
    @ResponseBody
    public String verificationCodeRegister2(@PathVariable("phone") String phone, @PathVariable("vc") String vc, @PathVariable("rpwd") String rpwd, HttpServletResponse response) {
        Boolean b = lsi.verificationCode(phone, vc);
        if (b == null) {
            System.out.println("注册验证码错误***");
            b = false;
        }
        if (b) {
            System.out.println("获取用户信息***");
            return lsi.saveRegisterUser(phone, rpwd, null, response);
        } else {
            return "注册失败";
        }

    }

    @CrossOrigin
    @RequestMapping("/cp/{phone}/{pwd}")
    @ResponseBody
    public String changePwd(@PathVariable("phone") String phone, @PathVariable("pwd") String pwd) {
        System.out.println("准备修改密码***");
        return lsi.changePwd(phone, pwd);
    }

}
