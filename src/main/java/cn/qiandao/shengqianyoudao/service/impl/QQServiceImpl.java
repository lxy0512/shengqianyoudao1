package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.config.Constants;
import cn.qiandao.shengqianyoudao.mapper.QQMapper;
import cn.qiandao.shengqianyoudao.pojo.QQthirdinfo;
import cn.qiandao.shengqianyoudao.service.QQService;
import cn.qiandao.shengqianyoudao.util.HttpClientUtils;
import cn.qiandao.shengqianyoudao.util.URLEncodeUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QQServiceImpl implements QQService {

    /**
     * QQ ：读取Appid相关配置信息静态类
     */
    @Autowired
    private Constants constants;
    @Autowired
    private QQMapper qqMapper;

    private static JSONObject parseJSONP(String jsonp){
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");

        String json = jsonp.substring(startIndex + 1,endIndex);

        return JSONObject.parseObject(json);
    }

    @Override
    public Map<String, Object> getToken(String code) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=authorization_code");

        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&code=" + code);
        // 回调地址
        String redirect_uri = constants.getQqRedirectUrl();
        // 转码
        url.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));
        // 获得token
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        if (result.startsWith("token:callback")){
            return null;
        }
        System.out.println(result);
        // 把token保存
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        //token信息
        Map<String,Object > qqProperties = new HashMap<String,Object >();
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", refreshToken);
        return qqProperties;

    }

    @Override
    public Map<String, Object> refreshToken(Map<String, Object> qqProperties) throws Exception {
        // 获取refreshToken
        String refreshToken = (String) qqProperties.get("refreshToken");
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=refresh_token");
        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&refresh_token=" + refreshToken);
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        // 把新获取的token存到map中
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String newRefreshToken = StringUtils.substringAfterLast(items[2], "=");
        //重置信息
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", newRefreshToken);
        return qqProperties;
    }

    @Override
    public String getOpenId(Map<String, Object> qqProperties) throws Exception {
        if (qqProperties==null){
            return "未授权";
        }
        // 获取保存的用户的token
        String accessToken = (String) qqProperties.get("accessToken");
        if (!StringUtils.isNotEmpty(accessToken)) {
            return "未授权";
        }
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
        url.append("access_token=" + accessToken);
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        return openId;
    }

    /**
     * 根据token,openId获取第三方用户信息
     *
     * @param qqProperties
     */
    @Override
    public QQthirdinfo getthirdUserInfo(Map<String, Object> qqProperties) throws Exception {
        String openId = (String) qqProperties.get("openId");

        return null;
    }

    /**
     * 根据openId判断用户是否绑定QQ
     *
     * @param openId
     */
    @Override
    public QQthirdinfo getUserInfo(String openId) {
        QQthirdinfo qQthirdinfo = new QQthirdinfo();
        qQthirdinfo.setQqOpenid(openId);
        QQthirdinfo qQthirdinfo1 = qqMapper.selectOne(qQthirdinfo);
        if (qQthirdinfo1 != null){
            return qQthirdinfo1;
        }
        return null;
    }
}
