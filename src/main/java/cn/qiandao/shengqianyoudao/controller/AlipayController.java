package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.config.AlipayConfig;
import cn.qiandao.shengqianyoudao.service.PayService;
import cn.qiandao.shengqianyoudao.service.SkillorderService;
import cn.qiandao.shengqianyoudao.util.PayState;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.ijpay.alipay.AliPayApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lxy
 * @date 2020/1/8 0008 17:28
 **/
@Slf4j
@Controller
@RequestMapping("/aliPay")
@CrossOrigin
public class AlipayController {

    @Autowired
    private PayService payService;
    @Autowired
    private SkillorderService skillorderService;

    @GetMapping(value = "/wapPay/{title}/{skillorderid}/{money}")
    @ResponseBody
    public void wapPay(HttpServletResponse response,@PathVariable("title") String title,@PathVariable("skillorderid") Long skillorderid,@PathVariable("money") double money) {
        log.info(title + "-" + skillorderid + "-" + money);
        payService.wapPay(response, title,skillorderid,money);
    }

    @RequestMapping(value = "/return_url")
    @ResponseBody
    public int returnUrl(HttpServletRequest request) {
        return payService.returnUrl(request);
    }

    @GetMapping(value = "/queryPay/{orderid}")
    @ResponseBody
    public PayState queryAliPayState(@PathVariable("orderid") Long orderId){
        return payService.queryAliPayState(orderId);
    }

    @RequestMapping(value = "/tradeRefund")
    @ResponseBody
    public String tradeRefund(@RequestParam(required = false, name = "outTradeNo") String outTradeNo, @RequestParam(required = false, name = "tradeNo") String tradeNo) {
        return "";
    }


    /**
     * 退款查询
     *
     * @param orderNo       商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/refundQuery")
    @ResponseBody
    public String refundQuery(String orderNo, String refundOrderNo) throws AlipayApiException {
        return "";
    }
    /**
     * 关闭交易
     *
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/close")
    @ResponseBody
    public String close(String orderNo) throws AlipayApiException {
        return "";
    }

}
