package cn.qiandao.shengqianyoudao.vo;

import lombok.Data;

/**
 * 返回结果封装
 * @author fyy
 * @date 2019/12/30 17:59
 **/
@Data
public class ServiceCoreVO {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回状态
     */
    private String status;

    public ServiceCoreVO() {
    }

    public ServiceCoreVO(String status) {
        this.status = status;
    }

    public ServiceCoreVO(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
