package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
public interface UserInfoService {

    String changeUserInfo(User user);
    ArrayList<Breviary> attention(String usernumber, int code);
    String invitationCode(String usernumber,String code);
    Breviary invitationCodeNull(String usernumber);
    String deleteUser(String usernumber);
    List<Breviary> getAllUserInfo();
    User getuserinfo(String usernumber);
}
