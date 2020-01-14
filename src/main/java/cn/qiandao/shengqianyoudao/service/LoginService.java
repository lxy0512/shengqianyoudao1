package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author wt
 * @data
 */
public interface LoginService {
    String getPasswordByUsername(String name);
    void getcode(String phone, int tcNumber);
    Boolean verificationCode(String sphone, String vc);
    String saveRegisterUser(String sphone, String pwd, String icode, HttpServletResponse response);
    Boolean verifyPhoneNumber(String sphone);
    User getUserInfo(String phone);
    String getcount();
    String changePwd(String phone, String pwd);
    User validationCookie(HttpServletRequest request);
}
