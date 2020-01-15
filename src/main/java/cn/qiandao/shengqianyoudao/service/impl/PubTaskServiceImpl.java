package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.TaskinfoMapper;
import cn.qiandao.shengqianyoudao.pojo.Taskinfo;
import cn.qiandao.shengqianyoudao.service.PubTaskService;
import cn.qiandao.shengqianyoudao.util.CheckSensitiveWords;
import cn.qiandao.shengqianyoudao.util.DateTimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class PubTaskServiceImpl implements PubTaskService {
    @Autowired
    private TaskinfoMapper taskinfoMapper;
    @Autowired
    private CheckSensitiveWords checkSensitiveWords;
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
    @Override
    public int insertTask(Map communityInfo) {
        boolean taskTitle = checkText(communityInfo.get("taskTitle").toString());
        boolean taskText = checkText(communityInfo.get("taskText").toString());
        System.out.println(communityInfo.get("taskRewardNumber")+"——————————————————————————————————————————————————");
        Date date = new Date();
        if (taskText == true && taskTitle == true){
            BigDecimal money = new BigDecimal(communityInfo.get("taskRewardMoney").toString());
            Taskinfo taskinfo = new Taskinfo();
            taskinfo.setTiTasknumber(addTaskNumber());
            taskinfo.setTiUsernumber(communityInfo.get("userId").toString());
            taskinfo.setTiTitle(communityInfo.get("taskTitle").toString());
            taskinfo.setTiType(Integer.parseInt(communityInfo.get("taskType").toString()));
            taskinfo.setTiMoney(money);
            taskinfo.setTiExperience(10*Integer.parseInt(communityInfo.get("taskRewardMoney").toString()));
            taskinfo.setTiPeoplelimit(Integer.parseInt(communityInfo.get("taskRewardNumber").toString()));
            taskinfo.setTiDatelimit(DateTimeUntil.strToDate(checkTime(Integer.parseInt(communityInfo.get("taskTime").toString()))));
            taskinfo.setTiAudit(Integer.parseInt(communityInfo.get("auditId").toString()));
            taskinfo.setTiTimelimit(Integer.parseInt(communityInfo.get("taskCommitTime").toString()));
            taskinfo.setTiTimeslimit(Integer.parseInt(communityInfo.get("taskRewardNumber").toString()));
            taskinfo.setTiDescribe(communityInfo.get("taskText").toString());
            taskinfo.setTiRequire(communityInfo.get("taskNeed").toString());
            taskinfo.setTiLink(communityInfo.get("taskLink").toString());
            taskinfo.setTiCompletepeople(0);
            taskinfo.setTiMsimg(changeImg(communityInfo.get("tiMsimg").toString()));
            taskinfo.setTiYqimg(changeImg(communityInfo.get("tiYqimg").toString()));
            taskinfo.setTiPageview(0);
            taskinfo.setTiState(1);
            taskinfo.setTiDate(date);
            int insert = taskinfoMapper.insert(taskinfo);
            return insert;
        }
        return 0;
    }

    @Override
    public String addTaskNumber() {
        String maxtasknumber = taskinfoMapper.getMaxtasknumber();
        int num = Integer.parseInt(maxtasknumber.substring(2));
        System.out.println("num："+num);
        maxtasknumber = "rw"+String.format("%05d",num+1);
        System.out.println("maxtasknumber："+maxtasknumber);
        return maxtasknumber;
    }

    @Override
    public boolean checkText(String text) {
        boolean b = checkSensitiveWords.checkWorks(text);
        return b;
    }

    @Override
    public String checkTime(int endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,endTime);
        Date date1 = calendar.getTime();
        String endString = simpleDateFormat.format(date1);
        return endString;
    }

    @Override
    public String changeImg(String imgList) {
        String imgString = imgList.substring(1,imgList.length()-1);
        return imgString;
    }
}
