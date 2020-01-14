package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.QQthirdinfo;

import java.util.Map;

public interface QQService {

    /**
     * 第一步:通过https://graph.qq.com/oauth2.0/authorize获得code值
     * 第二步:通过https://graph.qq.com/oauth2.0/token获取token
     */

    Map<String, Object> getToken(String code) throws Exception;
    /**
     * 刷新token 信息（token过期，重新授权）
     */
    Map<String,Object> refreshToken(Map<String, Object> qqProperties) throws Exception;

    /**
     *  第三步:通过token 获取open_id https://graph.qq.com/oauth2.0/me
     */
    String getOpenId(Map<String, Object> qqProperties) throws Exception;

    /**
     * 根据token,openId获取第三方用户信息
     */
    QQthirdinfo getthirdUserInfo(Map<String, Object> qqProperties) throws Exception;

    /**
     * 根据openId判断用户是否绑定QQ
     */
    QQthirdinfo getUserInfo(String openId);

}
