package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import cn.qiandao.shengqianyoudao.service.CollectionrecordsService;
import cn.qiandao.shengqianyoudao.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    private CollectionrecordsService collectionrecordsService;
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
    @GetMapping("/{skillsId}/{userId}")
    public String likeSkills(@PathVariable("skillsId") String skillsId,
                              @PathVariable("userId") String uId) {
        redisService.likeArticle(skillsId, uId);
        return "收藏成功";
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
    public String unlikeSkills(@PathVariable("skillsId") String skillsId,
                                @PathVariable("userId") String userId) {
        redisService.unlikeArticle(skillsId, userId);
        return "取消收藏";
    }

    /**
     * 查看收藏的技能
     */
    @ApiOperation(value="查看收藏的技能",notes="查看收藏的技能")
    @ApiImplicitParam(name="userId",value="用户编号")
    @GetMapping("/{userId}")
    public Object selectlikeSkills(@PathVariable("userId") String userId) {
        List<Skillsinfo> list = collectionrecordsService.selectAll(userId);
        /*if (list == null){
            return "空";
        }*/
        return list;
    }
}
