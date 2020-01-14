package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skilltype;
import cn.qiandao.shengqianyoudao.service.SkilltypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/5 0005 16:24
 **/
@RestController
@CrossOrigin
@RequestMapping("/skilltype")
@Api(value="/skilltype",description="技能类型API")
public class SkilltypeController {
    @Autowired
    private SkilltypeService skilltypeService;

    @GetMapping("/sel")
    @ApiImplicitParam(name = "stid", value = "技能类型编号", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getByFamilyAll(@RequestParam(value = "stid", defaultValue = "jn0098") String siFamilyId){
        return skilltypeService.getByFamilyAll(siFamilyId);
    }

    /**zrf
     * 发布技能二级类目
     * 心理咨询 jn0001 网络营销 jn0008 游戏电玩 jn0016 专业咨询 jn0030
     * @param gamesFather
     * @return
     */
    @GetMapping("getGames/{gamesFather}")
    @ApiImplicitParam(name = "gamesFather", value = "发布技能分类", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getGames(@PathVariable("gamesFather")String gamesFather){
        return skilltypeService.getByFamilyAll(gamesFather);
    }

    /**zrf
     * 专业技能 jn0039
     * 二级分类 视觉设计jn0040 语言翻译jn0049 影视动漫jn0066 编程技术jn0073 文案策划jn0082 新奇创意jn0090
     * @param skillId
     * @return
     */
    @GetMapping("getSkills/{skillId}")
    @ApiImplicitParam(name = "gamesSkill", value = "专业技能", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getSkills(@PathVariable("skillId")String skillId){
        return skilltypeService.getByFamilyAll(skillId);
    }



}
