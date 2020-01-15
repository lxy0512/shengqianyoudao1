package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.service.PubQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RequestMapping("/pubQuestion")
@RestController
public class pubQuestionController {
    @Autowired
    private PubQuestionService pubQuestionService;

    @RequestMapping("/pubPersonInfo")
    public String pubPersonInfo(@RequestBody Map personInfo){
        int result = pubQuestionService.insertPerson(personInfo);
        if (result == 1) {
            return "个人问卷信息填写成功 已经进入审核";
        }else {
            return "发布失败";
        }
    }

    @RequestMapping("/pubAllianceInfo")
    public String pubAllianceInfo(@RequestBody Map alliance){
        int result = pubQuestionService.insertAlliance(alliance);
        if (result == 1) {
            return "联盟问卷信息填写成功 已经进入审核";
        }else {
            return "发布失败";
        }
    }
}
