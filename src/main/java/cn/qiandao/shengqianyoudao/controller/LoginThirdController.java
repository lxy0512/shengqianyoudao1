package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.config.Constants;
import cn.qiandao.shengqianyoudao.pojo.QQthirdinfo;
import cn.qiandao.shengqianyoudao.pojo.Userinfo;
import cn.qiandao.shengqianyoudao.service.QQService;
import cn.qiandao.shengqianyoudao.service.UserService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * qq登录
 *
 * @author lxy
 * @date 2019年12月25日 17:01
*/

@Slf4j
@Controller
@RequestMapping("/api/oauth")
@CrossOrigin
public class LoginThirdController {
    @Autowired
    Constants constants;
    @Autowired
    private QQService qqService;
    @Autowired
    private UserService userService;

    @GetMapping("/thirdLogin/{type}")
    @ResponseBody
    public void thirdLogin(@PathVariable("type") int type, HttpServletResponse resp){
        return;
    }


    /**
     * 发起请求
     * @param session
     * @return
     */
    @GetMapping("/qqlogin")
    @ResponseBody
    public String qq(HttpSession session,HttpServletResponse resp) throws Exception{
        //QQ互联中的回调地址
        String backUrl = constants.getQqRedirectUrl();
        //用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        session.setAttribute("state",uuid);
        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code"+
                "&client_id=" + constants.getQqAppId() +
                "&redirect_uri=" + backUrl +
                "&state=" + uuid;
        return url;
    }

    @GetMapping("/QQ/callback")
    @ResponseBody
    public void QQLogin(HttpServletRequest request,HttpServletResponse resp) throws Exception {
        String code = request.getParameter("code");
        if (code != null) {
            System.out.println("code：" + code);
            //获取tocket
            Map<String, Object> qqProperties = qqService.getToken(code);
            //获取openId(每个用户的openId都是唯一不变的)
            String openId = qqService.getOpenId(qqProperties);
            System.out.println("openid：" + openId);
            QQthirdinfo userInfo = qqService.getUserInfo(openId);
            log.info("QQ登录信息：" + userInfo);
            if (userInfo == null){
                resp.setHeader("openId",openId);
                resp.sendRedirect("http://qiandao.xgl6.top:8080/#/register");
                return;
            }
            Userinfo byId = userService.findById(userInfo.getQqUsernumber());
            log.info("登录成功用户信息" + byId);
            resp.setHeader("User", byId.toString());
            resp.sendRedirect("http://qiandao.xgl6.top/#/home");
        }
    }
}

