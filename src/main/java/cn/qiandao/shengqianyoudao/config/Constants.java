package cn.qiandao.shengqianyoudao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * qq 登陆常量配置类
 */

@Component
@ConfigurationProperties("qq")
public class Constants {


    private String qqAppId;

    private String qqAppSecret;

    private String qqRedirectUrl;

    public String getQqAppId() {
        return qqAppId;
    }

    public void setQqAppId(String qqAppId) {
        this.qqAppId = qqAppId;
    }

    public String getQqAppSecret() {
        return qqAppSecret;
    }

    public void setQqAppSecret(String qqAppSecret) {
        this.qqAppSecret = qqAppSecret;
    }

    public String getQqRedirectUrl() {
        return qqRedirectUrl;
    }

    public void setQqRedirectUrl(String qqRedirectUrl) {
        this.qqRedirectUrl = qqRedirectUrl;
    }
}
