package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.service.PubQuestionService;
import cn.qiandao.shengqianyoudao.service.PubTaskService;
import cn.qiandao.shengqianyoudao.service.PublishSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PubQuestionServiceImpl implements PubQuestionService {
    @Autowired
    private PubTaskService pubTaskService;
    @Autowired
    private PublishSkillService publishSkillService;

    @Override
    public int insertPerson(Map persion) {
        System.out.println("persion"+persion);
        int budget = Integer.parseInt(persion.get("budget").toString());
        int taskRewardMoney = Integer.parseInt(persion.get("taskRewardMoney").toString());
        int taskRewardNumber = budget/taskRewardMoney;
        String taskLink = persion.get("taskLink").toString().substring(1,persion.get("taskLink").toString().length()-1);
        System.out.println("taskRewardNumber:"+taskRewardNumber+"budget:"+budget+"taskRewardMoney"+taskRewardMoney);
        Map newMap = new HashMap();
        newMap.put("userId",persion.get("userId"));
        newMap.put("taskType",persion.get("taskType"));
        newMap.put("taskTitle",persion.get("taskTitle"));
        newMap.put("taskText",persion.get("taskText"));
        newMap.put("tiMsimg",persion.get("tiMsimg"));
        newMap.put("taskNeed",persion.get("taskNeed"));
        newMap.put("tiYqimg",persion.get("tiYqimg"));
        newMap.put("taskFinishNumber",persion.get("taskFinishNumber"));
        newMap.put("taskWaitCheckTime",0);
        newMap.put("taskRewardNumber",Integer.toString(taskRewardNumber));
        System.out.println("Integer.toString(taskRewardNumber):----------"+Integer.toString(taskRewardNumber));
        newMap.put("taskRewardMoney",persion.get("taskRewardMoney"));
        newMap.put("taskTime",persion.get("taskTime"));
        newMap.put("taskCommitTime",0);
        newMap.put("auditId",persion.get("auditId"));
        newMap.put("taskLink",taskLink);
        System.out.println("newMap:"+persion);
        int result = pubTaskService.insertTask(newMap);
        System.out.println("result:"+result);
        return result;
    }

    @Override
    public int insertAlliance(Map alliance) {
        int budget = Integer.parseInt(alliance.get("budget").toString());
        int taskRewardMoney = Integer.parseInt(alliance.get("taskRewardMoney").toString());
        int taskRewardNumber = budget/taskRewardMoney;
        int chooes = Integer.parseInt(alliance.get("questionWay").toString());
        String text = "";
        switch (chooes) {
            case 1:
                text = "乐调查";
                break;
            case 2:
                text = "调研邦";
                break;
            case 3:
                text = "天天爱查";
                break;
            case 4:
                text = "ABC调查";
                break;
            case 5:
                text = "51调查网";
                break;
            case 6:
                text = "天天调查";
                break;
            case 7:
                text = "卓思维";
                break;
            case 8:
                text = "问卷网";
                break;
            default:
                break;
        }
        alliance.put("taskText",text);
        System.out.println("taskRewardNumber:"+taskRewardNumber+"budget:"+budget+"taskRewardMoney"+taskRewardMoney);
        Map newMap = new HashMap();
        newMap.put("userId",alliance.get("userId"));
        newMap.put("taskType",alliance.get("taskType"));
        newMap.put("taskTitle",alliance.get("taskTitle"));
        newMap.put("taskText",alliance.get("taskText"));
        newMap.put("tiMsimg",alliance.get("tiMsimg"));
        newMap.put("taskNeed",alliance.get("taskNeed"));
        newMap.put("tiYqimg",alliance.get("tiYqimg"));
        newMap.put("taskFinishNumber",alliance.get("taskFinishNumber"));
        newMap.put("taskWaitCheckTime",0);
        newMap.put("taskRewardNumber",Integer.toString(taskRewardNumber));
        System.out.println("Integer.toString(taskRewardNumber):----------"+Integer.toString(taskRewardNumber));
        newMap.put("taskRewardMoney",alliance.get("taskRewardMoney"));
        newMap.put("taskTime",alliance.get("taskTime"));
        newMap.put("taskCommitTime",0);
        newMap.put("auditId",0);
        newMap.put("taskLink",0);
        System.out.println("newMap:"+alliance);
        int result = pubTaskService.insertTask(newMap);
        System.out.println("result:"+result);
        return result;
    }
}
