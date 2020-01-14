
package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.config.AlipayConfig;
import cn.qiandao.shengqianyoudao.service.PayService;
import cn.qiandao.shengqianyoudao.service.SkillorderService;
import cn.qiandao.shengqianyoudao.util.PayState;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author xgl
 * @create 2020-01-07 19:14
 */

@Service
public class PayServiceImpl implements PayService {

    private Logger log = LoggerFactory.getLogger(PayServiceImpl.class);
    @Autowired
    private SkillorderService skillorderService;

    public void getApiConfig() {
        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.builder()
                .setAppId(AlipayConfig.app_id)
                .setAliPayPublicKey(AlipayConfig.alipay_public_key)
                .setCharset("UTF-8")
                .setPrivateKey(AlipayConfig.merchant_private_key)
                .setServiceUrl(AlipayConfig.gatewayUrl)
                .setSignType("RSA2")
                .build(); // 普通公钥方式
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);
    }

    /**
     * 创建阿里支付URl
     *
     * @param
     * @return
     */
    @Override
    public void wapPay(HttpServletResponse response, String title, Long skillorderid, double money) {
        getApiConfig();
        String returnUrl = AlipayConfig.return_url;
        String notifyUrl = AlipayConfig.notify_url;

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setSubject(title);
        model.setTotalAmount(String.valueOf(money));
        model.setTimeoutExpress("2m");
        model.setOutTradeNo(String.valueOf(skillorderid));
        model.setProductCode("QUICK_WAP_PAY");
        log.info("wap outTradeNo>" + String.valueOf(skillorderid));
        try {
            AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public int returnUrl(HttpServletRequest request) {
        log.info(">>>>>>>>支付成功, 进入同步通知接口...");
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            boolean verifyResult = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipay_public_key, "UTF-8",
                    "RSA2");
            if (verifyResult) {
                log.info("return_url 验证成功");
                return 1;
            } else {
                log.info("return_url 验证失败");
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 2;
    }

    @Override
    public PayState notifyUrl(HttpServletRequest request) {
        log.info(">>>>>>>>支付成功, 进入异步通知接口...");
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);

            for (Map.Entry<String, String> entry : params.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", "RSA2");

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
                System.out.println("notify_url 验证成功succcess");
                return PayState.SUCCESS;
            } else {
                System.out.println("notify_url 验证失败");
                // TODO
                return PayState.FAIL;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return PayState.FAIL;
        }
    }

    /**
     * 查询阿里付款状态
     *
     * @param orderId
     * @return
     */
    @Override
    public PayState queryAliPayState(Long orderId) {
        getApiConfig();
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(orderId.toString());

            String body = AliPayApi.tradeQueryToResponse(model).getBody();
            if (body == null) {
                //未查询到付款结果,认为是未付款
                return PayState.NOT_PAY;
            }
            JSONObject jsonObject = JSONObject.parseObject(body);
            log.info("序列化结果{}", jsonObject);
            String result = jsonObject.getJSONObject("alipay_trade_query_response").getString("trade_status");
            log.info("支付宝状态:{}", result);
            if (result == null) {
                return PayState.FAIL;
            }
            if (StringUtils.equals(result, "TRADE_SUCCESS")) {
                //此时,交易支付成功
                skillorderService.updateOrder(orderId, 2);
                return PayState.SUCCESS;
            }
            if (StringUtils.equals(result, "WAIT_BUYER_PAY")) {
                //此时,交易创建，等待买家付款
                return PayState.NOT_PAY;
            }
            if (StringUtils.equals(result, "TRADE_CLOSED")) {
                //此时,未付款交易超时关闭，或支付完成后全额退款
                return PayState.FAIL;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款
     */
    public String tradeRefund(String outTradeNo, String tradeNo) {
        getApiConfig();
        try {
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            if (StringUtils.isNotEmpty(outTradeNo)) {
                model.setOutTradeNo(outTradeNo);
            }
            if (StringUtils.isNotEmpty(tradeNo)) {
                model.setTradeNo(tradeNo);
            }
            //Skillorder skillorder = skillorderService.selectOrder(outTradeNo);
            //model.setRefundAmount(skillorder.getSoMoney().toString());
            model.setRefundAmount("5");
            model.setRefundReason("正常退款");
            return AliPayApi.tradeRefundToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款查询
     *
     * @param orderNo       商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    public String refundQuery(String orderNo, String refundOrderNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        try {
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
            if (StringUtils.isNotEmpty(orderNo)) {
                model.setOutTradeNo(orderNo);
            }
            if (StringUtils.isNotEmpty(refundOrderNo)) {
                model.setTradeNo(refundOrderNo);
            }
            alipayRequest.setBizModel(model);
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
            AlipayTradeFastpayRefundQueryResponse alipayResponse = alipayClient.execute(alipayRequest);
            System.out.println(alipayResponse.getBody());
            return alipayResponse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭交易
     *
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    public String close(String orderNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        try {
            AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            if (StringUtils.isNotEmpty(orderNo)) {
                model.setOutTradeNo(orderNo);
            }
            model.setOutTradeNo(orderNo);
            alipayRequest.setBizModel(model);
            AlipayTradeCloseResponse alipayResponse = alipayClient.execute(alipayRequest);
            System.out.println(alipayResponse.getBody());
            return alipayResponse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

