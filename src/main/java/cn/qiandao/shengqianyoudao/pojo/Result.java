package cn.qiandao.shengqianyoudao.pojo;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;//是否成功

    private Object message;//返回信息


    public Result() {
    }

    public Result(boolean success, Object message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
