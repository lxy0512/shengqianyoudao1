package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.PostPojo;
import cn.qiandao.shengqianyoudao.pojo.PublishGameSkill;
import cn.qiandao.shengqianyoudao.pojo.Skilltype;
import cn.qiandao.shengqianyoudao.service.IImagesService;
import cn.qiandao.shengqianyoudao.service.PublishSkillService;
import cn.qiandao.shengqianyoudao.service.SkilltypeService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@CrossOrigin
@RestController
@RequestMapping("/pubSkill")
public class PublishSkillController {
    @Autowired
    private PublishSkillService publishSkillService;
    @Autowired
    private SkilltypeService skilltypeService;
    @Autowired
    private IImagesService iImagesService;
    /**
     * 发布技能二级类目
     * 心理咨询 jn0001 网络营销 jn0008 游戏电玩 jn0016 专业咨询 jn0030 专业技能 jn0039
     * 二级分类 视觉设计jn0040 语言翻译jn0049 影视动漫jn0066 编程技术jn0073 文案策划jn0082 新奇创意jn0090
     * @param skillId
     * @return
     */
    @GetMapping("/getSkills/{skillId}")
    //@ApiImplicitParam(name = "skillId", value = "发布技能分类", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getGames(@PathVariable("skillId")String skillId){
        int skillNum = Integer.parseInt(skillId);
        List skill = publishSkillService.getSkill(skillNum);
        return skill;
    }

    @GetMapping("/getGameSkills/{gameSkillId}")
    //@ApiImplicitParam(name = "skillId", value = "发布技能分类", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getGameSkills(@PathVariable("gameSkillId")String gameSkillId){
        int skillNum = Integer.parseInt(gameSkillId);
        List list = publishSkillService.getprofessionalSkill(skillNum);
        return list;
    }

    /**
     * 获取个人风格
     * @param personStyleId
     * @return
     */
    @GetMapping("/getPersonStyle/{personStyleId}")
    @ResponseBody
    @ApiImplicitParam(name = "personStyleId", value = "个人风格id", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getpersonStyle(@PathVariable("personStyleId")String personStyleId){
        System.out.println(personStyleId);
        personStyleId = personStyleId + "-1";
        return skilltypeService.getByFamilyAll(personStyleId);
    }

    /**
     * 获取段位等级
     * @param gradeLevelId
     * @return
     */
    @GetMapping("getGradeLevel/{gradeLevelId}")
    @ResponseBody
    @ApiImplicitParam(name = "personStyleId", value = "段位等级id", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getgradeLevel(@PathVariable("GradeLevelId")String gradeLevelId){
        gradeLevelId = gradeLevelId + "-2";
        return skilltypeService.getByFamilyAll(gradeLevelId);
    }

    /**
     * 获取游戏区服
     * @param gameAreaId
     * @return
     */
    @GetMapping("getGameArea/{gameAreaId}")
    @ResponseBody
    @ApiImplicitParam(name = "gameAreaId", value = "游戏区服id", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getGameArea(@PathVariable("GradeLevelId")String gameAreaId){
        gameAreaId = gameAreaId + "-3";
        return skilltypeService.getByFamilyAll(gameAreaId);
    }

    /**
     * 获取操作系统
     * @param gameSystemId
     * @return
     */
    @GetMapping("getSystem/{gameSystemId}")
    @ResponseBody
    @ApiImplicitParam(name = "gameSystemId", value = "操作系统id", required = true, dataType = "string", paramType = "path")
    public List<Skilltype> getSystem(@PathVariable("gameSystemId")String gameSystemId){
        gameSystemId = gameSystemId + "-4";
        return skilltypeService.getByFamilyAll(gameSystemId);
    }

    /**
     * 发布技能
     * @param userMap
     * @return
     */
    @RequestMapping("/insertSkill")
    @ResponseBody
    public String insertSkill(@RequestBody Map userMap){
        System.out.println("已进入发布技能");
        System.out.println(userMap);
        /*userMap.put("userId",userId);
        userMap.put("siTitle",siTitle);
        userMap.put("siType",siType);
        userMap.put("siMoney",siMoney);
        userMap.put("siDescribe",siDescribe);
        userMap.put("siDuration",siDuration);
        userMap.put("siDate",siDate);
        userMap.put("siImg",siImg);
        userMap.put("siModifynumber",siModifynumber);*/
        String putResult = publishSkillService.pubSkill(userMap);
        System.out.println(putResult);
        return putResult;
    }

    /**
     * 发布游戏技能
     * @param PublishGameSkill
     * @return
     */
    @RequestMapping("/pubGameSkill")
    @ResponseBody
    public String pubGameSkill(@RequestBody PublishGameSkill PublishGameSkill){
        System.out.println("已进入发布游戏技能");
        System.out.println(PublishGameSkill);
        String putResult = publishSkillService.modifyContent(PublishGameSkill);
        System.out.println("pubGameSpubGameSkillkill-controller结果："+putResult);
        return putResult;
    }

    /**
     * 获取发布帖子二级类目
     * @param
     * @return
     */
    @GetMapping("/getPostclassification")
    public List getPostclassification(){
        System.out.println("已进入发布帖子");
        List postFamliy = publishSkillService.getPostFamliy("发布帖子");
        return postFamliy;
    }

    @RequestMapping("/pubPost")
    public String pubPost(@RequestBody Map postMap){
        System.out.println(postMap+"1111111111111111111111");
        System.out.println("已进入发布帖子");
        /**
         * piUsernumber piTitle piType piContent piImg
         */
        String result = publishSkillService.pubPost(postMap);
        return result;
    }

}
