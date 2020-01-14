package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Taskorder;

import java.util.List;

/**
 * @author fyy
 * @date 2020/1/9 16:49
 **/
public interface TaskorderService {
    /**
     * 展示不同状态的任务数量
     * @param user 用户
     * @param state 状态
     * @return
     */
    int getOrderCount(String user, int state);

    /**
     * 展示不同状态的任务
     * @param user 用户
     * @param state 状态
     * @return
     */
    List<Taskorder> getOrderState(String user, int state);
}
