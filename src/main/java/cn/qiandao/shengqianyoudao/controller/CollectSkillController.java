package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lxy
 * @date 2020/2/3 0003 17:36
 **/
@RestController
@Slf4j
@RequestMapping("/CollectSkill")
public class CollectSkillController {

    @Autowired
    private ISkillRelationService service;
    @Resource
    private RedisService redisService;

    @GetMapping("/getUser/{uId}")
    public Skilluserrelationship selUser(@PathVariable("uId") String skillsId) {
        return service.selUser(skillsId);
    }



    /**
     * 点赞
     */
    @PostMapping("/{skillsId}/{userId}")
    public Object likeArticle(@PathVariable("skillsId") String skillsId,
                              @PathVariable("userId") String uId) {
        redisService.likeArticle(skillsId, uId);
        return skillsId;
    }

    /**
     * 取消点赞
     */
    @DeleteMapping("/{skillsId}/{userId}")
    public Object unlikeArticle(@PathVariable("skillsId") String skillsId,
                                @PathVariable("userId") String userId) {
        redisService.unlikeArticle(skillsId, userId);
        return skillsId;
    }
}
