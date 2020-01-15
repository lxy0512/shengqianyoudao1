package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.service.PubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/pubTask")
public class PubTaskController {
    /**
     *     标题 taskTitle
     *     内容 taskText
     *     任务描述图片集合 tiMsimg
     *     任务要求图片集合 tiYqimg
     *     每人完成次数 taskFinishNumber
     *     奖励次数 taskRewardNumber
     *     奖励单价 taskRewardMoney
     *     任务持续时间 taskTime
     *     作品提交时间限制 taskCommitTime
     *     雇主审核时间 taskWaitCheckTime
     *     用户id userId
     *     发布类型 taskType
     *     是否需要审核 auditId
     *     任务要求 taskNeed
     *     taskLink
     */
    @Autowired
    private PubTaskService pubTaskService;
    @RequestMapping("/pubCommunity")
    public String pubCommunity(@RequestBody Map communityInfo){
        int result = pubTaskService.insertTask(communityInfo);
        if (result == 1) {
            return "发布社区互助任务成功 已经进入审核";
        }else {
            return "发布失败";
        }
    }

}
