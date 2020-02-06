package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value="/CollectSkill",description="收藏")
@CrossOrigin
public class CollectSkillController {

    @Autowired
    private ISkillRelationService service;
    @Resource
    private RedisService redisService;

    /**
     * 点赞
     */
    @ApiOperation(value="用户点赞收藏",notes="用户点赞收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="skillsId",value="技能编号"),
            @ApiImplicitParam(name="userId",value="用户编号")
    })
    @PostMapping("/{skillsId}/{userId}")
    public Object likeArticle(@PathVariable("skillsId") String skillsId,
                              @PathVariable("userId") String uId) {
        redisService.likeArticle(skillsId, uId);
        return skillsId;
    }

    /**
     * 取消点赞
     */
    @ApiOperation(value="用户取消点赞收藏",notes="用户取消点赞收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="skillsId",value="技能编号"),
            @ApiImplicitParam(name="userId",value="用户编号")
    })
    @DeleteMapping("/{skillsId}/{userId}")
    public Object unlikeArticle(@PathVariable("skillsId") String skillsId,
                                @PathVariable("userId") String userId) {
        redisService.unlikeArticle(skillsId, userId);
        return skillsId;
    }
}
