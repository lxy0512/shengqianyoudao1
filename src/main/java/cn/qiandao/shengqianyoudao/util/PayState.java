package cn.qiandao.shengqianyoudao.util;

public enum PayState {
    /**
     *交易创建，等待买家付款
     */
    NOT_PAY(0),
    /**
     *交易支付成功
     */
    SUCCESS(1),
    /**
     *未付款交易超时关闭，或支付完成后全额退款
     */
    FAIL(2);

    PayState(int value) {
        this.value = value;
    }

    int value;

    public int getValue() {
        return value;
    }
}
