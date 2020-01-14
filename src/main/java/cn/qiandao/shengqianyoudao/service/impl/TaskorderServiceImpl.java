package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.TaskorderMapper;
import cn.qiandao.shengqianyoudao.pojo.Taskorder;
import cn.qiandao.shengqianyoudao.service.TaskorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fyy
 * @date 2020/1/10 11:15
 **/
@Service
public class TaskorderServiceImpl implements TaskorderService {
    @Autowired
    private TaskorderMapper taskorderMapper;
    @Override
    public int getOrderCount(String user, int state) {
        Taskorder order = new Taskorder();
        order.setToReleaseusernumber(user);
        order.setToState(state);
        return taskorderMapper.selectCount(order);
    }

    @Override
    public List<Taskorder> getOrderState(String user, int state) {
        Taskorder order = new Taskorder();
        order.setToReleaseusernumber(user);
        order.setToState(state);
        return taskorderMapper.select(order);
    }
}
