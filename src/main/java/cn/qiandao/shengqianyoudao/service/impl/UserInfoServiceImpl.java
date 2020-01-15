package cn.qiandao.shengqianyoudao.service.impl;


import cn.qiandao.shengqianyoudao.mapper.UserInfoMapper;
import cn.qiandao.shengqianyoudao.pojo.User;
import cn.qiandao.shengqianyoudao.pojo.Breviary;
import cn.qiandao.shengqianyoudao.service.UserInfoService;
import cn.qiandao.shengqianyoudao.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper uim;
    @Autowired
    private DateTime dateTime;

    public String changeUserInfo(User user){
        int count = uim.changeUserInfo(user);
        int count2 = uim.changeUserBasicInfo(user);
        if (count + count2 > 0 ){
            return "修改数据成功";
        }else {
            return "修改数据失败";
        }
    }

    /**
     * 获取关注信息
     * @param usernumber
     * @param code
     * @return
     */
    public ArrayList<Breviary> attention(String usernumber, int code){
        if (usernumber == null){
            return null;
        }
        ArrayList<Breviary> l = null;
        switch (code){
            case 1:
                l = uim.attention1(usernumber);
                break;
            case 2:
                l = uim.attention2(usernumber);
                break;
        }
        System.out.println(l);
        return l;
    }

    /**
     * 邀请记录检查
     * @param usernumber
     * @return
     */
    public Breviary invitationCodeNull(String usernumber){
        Breviary iUser = uim.getIUser(usernumber);
        System.out.println(iUser);
        if (iUser == null){
            return null;
        }else {
            return iUser;
        }
    }

    /**
     * 补交填写邀请码
     * @param usernumber
     * @param code
     * @return
     */
    public String      invitationCode(String usernumber,String code){
        if(uim.getICode(code) == null){
            return "此邀请码不存在";
        }else {
            if(uim.repeatICode(usernumber,code) == 0){
                if(uim.saveICode(usernumber, code, dateTime.getDate()) > 0){
                    return "存储成功";
                }else {
                    return "存储异常";
                }
            }else {
                return "请勿重复邀请";
            }
        }
    }

    public String deleteUser(String usernumber){
        if (uim.deleteUser(usernumber) > 0){
            return "删除成功";
        }
        return "删除失败";
    }

    public User getuserinfo(String usernumber){
        return uim.getUserInfo(usernumber);
    }

    public List<Breviary> getAllUserInfo(String pagenum){
        int page = (Integer.valueOf(pagenum) -1)*5;
        return uim.getAllUserInfo(page);
    }

}
