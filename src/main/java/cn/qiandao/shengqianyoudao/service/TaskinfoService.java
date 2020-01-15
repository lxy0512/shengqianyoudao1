package cn.qiandao.shengqianyoudao.service;


import cn.qiandao.shengqianyoudao.pojo.Taskinfo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.runtime.Desc;

import java.util.List;

public interface TaskinfoService {
    /**
     * 通过任务编号id进行查询
     * @param taskid 任务编号
     * @return
     */
    Taskinfo selectTask(String taskid);

    /**
     * 查询分类的任务集合
     * @param type
     * @return
     */
    List<Taskinfo> selectAllTask(Integer type);

    /**
     * 价格排序  desc正序  asc倒序
     * @return count
     */
    List<Taskinfo> selectByExample(String type, String sort);

    /**
     * 查询用户发布的任务
     * @param user
     * @return
     */
    List<Taskinfo> selectTasks(String user);
    /**
     * 查询任务的数量(不同状态)
     * @param user 用户编号
     * @param state 状态
     * @return
     */
    int getTackCount(String user, int state);

    /**
     * 查询任务后展示(不同状态)
     * @param user 用户编号
     * @param state 状态
     * @return
     */
    List<Taskinfo> getTaskState(String user, int state);

    /**
     * 接受任务
     * @param tiPeoplelimit 截止人数
     * @param taskid 任务编号
     * @return
     */
    int acceptTask(int tiPeoplelimit, String taskid);

    //补全后台接口

    /**
     * 修改任务
     * @param taskid 任务编号
     * @param state 状态
     * @return
     */
    int updateTask(String taskid, int state);

    /**
     * 分页查询所有任务
     * @param state 不同状态
     * @param pageNum  //第几页
     * @param pageSize  //每页显示的个数
     * @return
     */
    PageInfo<Taskinfo> getAllTask(int state, int pageNum, int pageSize);
    /**
     * 通过任务id删除任务
     * @param taskid 任务编号
     * @return
     */
    int delTask(String taskid);
}
