package cn.qiandao.shengqianyoudao.pojo;

import javax.validation.constraints.Size;
import java.util.List;

public class PubTaskInfo {
    //标题
    private String taskTitle;
    //内容
    private String taskText;
    //图片集合
    private List taskImg;
    //每人完成次数
    private Integer taskFinishNumber;
    //奖励次数
    private Integer taskRewardNumber;
    //奖励单价
    private String taskRewardMoney;
    //任务持续时间
    private String taskTime;
    //作品提交时间限制
    private String taskCommitTime;
    //雇主审核时间
    private String taskWaitCheckTime;


}
