package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.LoginMapper;
import cn.qiandao.shengqianyoudao.pojo.User;
import cn.qiandao.shengqianyoudao.service.LoginService;
import cn.qiandao.shengqianyoudao.util.CookieUtils;
import cn.qiandao.shengqianyoudao.util.CreatUserName;
import cn.qiandao.shengqianyoudao.util.DateTime;
import cn.qiandao.shengqianyoudao.util.EncryptionToDecrypt;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author wt
 * @data
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper lm;
    @Autowired
    private cn.qiandao.shengqianyoudao.util.code code;
    @Autowired
    private DateTime date;
    @Autowired
    private CreatUserName creatUserName;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CookieUtils cookieUtils;
    @Autowired
    private EncryptionToDecrypt etd;

    @Override
    public String getPasswordByUsername(String name) {
        return lm.getPasswordByUsername(name);
    }

    //发送短信
    @Override
    public void getcode(String sphone, int tcNumber) {
        String templateNumber = "";
        switch (tcNumber) {
            case 1:
                templateNumber = "SMS_181202286";
                break;
            case 2:
                templateNumber = "SMS_181202289";
                break;
            case 3:
                templateNumber = "SMS_181490498";
                break;
            default:
                break;
        }
        if (stringRedisTemplate.opsForValue().get(sphone) == null) {
            stringRedisTemplate.opsForValue().set(sphone, code.getLoginCode(), 300, TimeUnit.SECONDS);
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FfrbTPfkkAtQNQWwzov", "h9rW4TuYGKbkjKlNaTt7z5MWOKYehF");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", sphone);
        request.putQueryParameter("SignName", "生钱有道");
        request.putQueryParameter("TemplateCode", templateNumber);
        request.putQueryParameter("TemplateParam", stringRedisTemplate.opsForValue().get(sphone));

        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("短信已发送----");
    }

    //比对验证码
    @Override
    public Boolean verificationCode(String phone, String vc) {
        String s = stringRedisTemplate.opsForValue().get(phone);
        if (s != null) {
            System.out.println("账户存在---");
            System.out.println(s.substring(9, 13));
            if (vc.equals(s.substring(9, 13))) {
                System.out.println(vc);
                System.out.println(s);
                System.out.println(s.substring(9, 13));
                System.out.println("验证码正确---");
                return true;
            } else {
                System.out.println("验证码错误---");
                return false;
            }
        } else {
            System.out.println("账户不存在---");
            return false;
        }

    }

    //用户注册成功进行信息存储
    @Override
    @Transactional
    public String saveRegisterUser(String phone, String pwd,String icode, HttpServletResponse response) {
        if (lm.getPhone(phone) == null) {
            System.out.println("新用户---");
            User u = new User();
            u.setNumber(code.getNewEquipmentNo("yh", getcount().substring(3)));
            u.setUsername(creatUserName.getNewUserName());
            u.setPwd(new Md5Hash(pwd, phone, 3).toString());
            u.setPhone(phone);
            u.setInvitationcode(code.getInvitationCode());
            u.setRegistrationdate(date.getDate());
            int count = lm.saveRegisterUser(u);
            //未更改
            int count2 = -1;
            if (lm.getUserByNumberOnRole(u.getNumber()) == null) {
                count2 = lm.saveUserRole(u.getNumber());
            }
            if (icode != null){
                lm.saveInvitationInfo(u.getNumber(),lm.getNumberByIcode(icode),date.getDate());
            }
            lm.saveNovTaskInfo(u.getNumber(),date.getDateTime(),"xs1");
            lm.saveNovTaskInfo(u.getNumber(),date.getDateTime(),"xs2");
            lm.saveNovTaskInfo(u.getNumber(),date.getDateTime(),"xs3");
            lm.saveNovTaskInfo(u.getNumber(),date.getDateTime(),"xs4");
            lm.saveNovTaskInfo(u.getNumber(),date.getDateTime(),"xs5");
            System.out.println("你的父级邀请码为"+icode);
            System.out.println(count);
            System.out.println(count2);
            if (count > 0 & count2 > 0) {
                System.out.println("信息存储成功---");
                System.out.println(u);
                return "注册成功";
            } else {
                System.out.println("存储异常---");
                return "注册异常";
            }
        } else {
            System.out.println("用户已存在---");
            return "注册失败";
        }
    }

    //比对手机号码是否已经注册
    @Override
    public Boolean verifyPhoneNumber(String phone) {
        if (lm.getPhone(phone) == null) {
            return false;
        } else {
            return true;
        }
    }

    //获取用户信息
    @Override
    public User getUserInfo(String phone) {
        User u = lm.getUserInfo(phone);
        return u;
    }

    @Override
    public String getcount() {
        return lm.getNumber();
    }

    /**
     * 密码登录
     * @param name
     * @param pwd
     * @param response
     * @param request
     * @return
     */
    public User ptlLogin(String name, String pwd, HttpServletResponse response,HttpServletRequest request) {
        User user = validationCookie(request);
        if (user != null){
            return user;
        }
        if (name != null && pwd != null) {
            System.out.println("比对信息---");
            user = getUserInfo(name);
            String s = new Md5Hash(pwd, name, 3).toString();
            if (user.getPwd() != null && user.getPwd().equals(s)) {
                System.out.println("获取用户信息---");
                cookieUtils.writeCookie(response, "sqyd", etd.AESEncode("123", name));
            }
        }
        System.out.println("user的值为------"+user);
        return user;
    }

    //尝试cookie登录
    @Override
    public User validationCookie(HttpServletRequest request) {
        String cookiephone = cookieUtils.getCookie(request, "sqyd");
        if (cookiephone != null){
            String phone = etd.AESDncode("123", cookiephone);
            if (lm.getPhone(phone) != null) {
                return lm.getUserInfo(phone);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public String changePwd(String phone, String pwd) {
        pwd = new Md5Hash(pwd, phone, 3).toString();
        System.out.println(phone);
        System.out.println(pwd);
        int count = lm.changPwd(phone, pwd);
        System.out.println(count);
        if (count > 0) {
            return "重置密码成功";
        } else {
            return "重置密码失败";
        }
    }
}