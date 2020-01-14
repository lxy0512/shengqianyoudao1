package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.util.PayState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lxy
 * @date 2020/1/9 0009 17:37
 **/
public interface PayService {
    void wapPay(HttpServletResponse response, String title, Long skillorderid, double money);
    PayState queryAliPayState(Long orderId);
    int returnUrl(HttpServletRequest request);
    PayState notifyUrl(HttpServletRequest request);
}
