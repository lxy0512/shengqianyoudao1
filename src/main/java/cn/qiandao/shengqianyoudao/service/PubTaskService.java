package cn.qiandao.shengqianyoudao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 发布任务
 */
public interface PubTaskService {
    /**
     *插入任务详情表
     */
    int insertTask(Map communityInfo);
    /**
     * 获取技能编号
     * @return
     */
    String addTaskNumber();
    /**
     * 判断文本是否符合规范
     * @param text
     * @return
     */
    boolean checkText(String text);
    /**
     * 增加时间
     * @param endTime
     * @return
     */
    String checkTime(int endTime);
    /**
     * 将图片集合转成string
     */
    String changeImg(String imgList);
}
