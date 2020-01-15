package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.service.PubCrowdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 发布众包任务
 * zrf
 */
@CrossOrigin
@RequestMapping("/pubCrowd")
@RestController
public class pubCrowdController {
    @Autowired
    private PubCrowdService pubCrowdService;

    @RequestMapping("/pubCrowdTask")
    public String pubCrowsTask(@RequestBody Map crowdTask) {
        int result = pubCrowdService.insertCrowd(crowdTask);
        if (result == 1) {
            return "您发布众包任务已进入审核";
        } else {
            return "发布众包任务失败";
        }
    }
}
