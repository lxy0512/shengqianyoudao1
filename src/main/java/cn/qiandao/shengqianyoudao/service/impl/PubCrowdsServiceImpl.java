package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.pojo.Taskinfo;
import cn.qiandao.shengqianyoudao.service.PubCrowdService;
import cn.qiandao.shengqianyoudao.service.PubTaskService;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class PubCrowdsServiceImpl implements PubCrowdService {
    @Autowired
    private PubTaskService pubTaskService;
    @Override
    public int insertCrowd(Map crowdInfo) {
        int budget = Integer.parseInt(crowdInfo.get("budget").toString());
        int taskRewardMoney = Integer.parseInt(crowdInfo.get("taskRewardMoney").toString());
        int taskRewardNumber = budget/taskRewardMoney;
        System.out.println("taskRewardNumber:"+taskRewardNumber+"budget:"+budget+"taskRewardMoney"+taskRewardMoney);
        Map newMap = new HashMap();
        newMap.put("userId",crowdInfo.get("userId"));
        newMap.put("taskType",crowdInfo.get("taskType"));
        newMap.put("taskTitle",crowdInfo.get("taskTitle"));
        newMap.put("taskText",crowdInfo.get("taskText"));
        newMap.put("tiMsimg",crowdInfo.get("tiMsimg"));
        newMap.put("taskNeed",crowdInfo.get("taskNeed"));
        newMap.put("tiYqimg",crowdInfo.get("tiYqimg"));
        newMap.put("taskFinishNumber",crowdInfo.get("taskFinishNumber"));
        newMap.put("taskWaitCheckTime",0);
        newMap.put("taskRewardNumber",Integer.toString(taskRewardNumber));
        System.out.println("Integer.toString(taskRewardNumber):----------"+Integer.toString(taskRewardNumber));
        newMap.put("taskRewardMoney",crowdInfo.get("taskRewardMoney"));
        newMap.put("taskTime",crowdInfo.get("taskTime"));
        newMap.put("taskCommitTime",0);
        newMap.put("auditId",crowdInfo.get("auditId"));
        newMap.put("taskLink",0);
        System.out.println("newMap:"+crowdInfo);
        int result = pubTaskService.insertTask(newMap);
        System.out.println("result:"+result);
        return result;
    }
}
