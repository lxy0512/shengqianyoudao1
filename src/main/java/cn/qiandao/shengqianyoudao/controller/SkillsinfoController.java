package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.*;
import cn.qiandao.shengqianyoudao.service.SkillcommentService;
import cn.qiandao.shengqianyoudao.service.SkillsinfoService;
import cn.qiandao.shengqianyoudao.service.UserService;
import cn.qiandao.shengqianyoudao.util.IDUtil;
import com.alipay.api.domain.Person;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxy
 * @date 2020/1/3 0003 23:13
 **/
@RestController
@CrossOrigin
@RequestMapping("/skills")
@Api(value="/cashrecord",description="技能信息API")
public class SkillsinfoController {
    @Autowired(required=true)
    private SkillsinfoService skillsinfoService;
    @Autowired
    private SkillcommentService skillcommentService;
    @Autowired
    private UserService userService;

    @GetMapping("/selAll/{state}")
    @ApiOperation(value = "查询所有技能信息", notes = "查询技能信息")
    public List<Skillsinfo> selectAll(@PathVariable("state")int state){
        return skillsinfoService.selectAll(state);
    }

    @GetMapping("/sel/{skillId}")
    @ApiOperation(value = "技能详情页信息", notes = "技能详情页信息")
    public Map<String,Object> getSkillOrder(@PathVariable("skillId") String skillId){

        Map<String,Object> map = new HashMap<String, Object>();
        try {
            Skillsinfo skillsinfo = skillsinfoService.selectBySiSerialnumber(skillId);
            System.out.println(skillsinfo);
            List<Skillcomment> skillcomments = skillcommentService.selSkillcomment(skillId);
            Skilluserrelationship user = skillsinfoService.getUser(skillId);
            Userinfo users = userService.findById(user.getSurUsernumber());
            /*if (users == null){
                return null;
            }*/
            map.put("skillsinfo",skillsinfo);
            map.put("skillcomments",skillcomments);
            map.put("users",users);
            return map;
        }catch (Exception e){
            throw new RuntimeException("参数错误");
        }
    }

    @GetMapping("/selOne/{skillId}")
    @ApiOperation(value = "技能详情加用户信息", notes = "技能详情页信息")
    public Skillsinfo getBySkills(String siSerialnumber){
        Skillsinfo skillsinfo = skillsinfoService.selectBySiSerialnumber(siSerialnumber);
        System.out.println(skillsinfo);
        return skillsinfo;
    }

    @GetMapping("/seluid/{skillId}")
    @ApiOperation(value = "查技能用户编号", notes = "查技能用户编号")
    public Skilluserrelationship selectAll(@PathVariable("skillId") String skillId){
        return skillsinfoService.getUser(skillId);
    }

    @GetMapping("/seluser/{uid}")
    @ApiOperation(value = "用户详情", notes = "用户详情")
    public Userinfo selectUser(@PathVariable("uid") String uid){
        return userService.findById(uid);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改技能", notes = "修改技能")
    public int updateSkillsBySkills(@RequestBody Skillsinfo skillsinfo) {
        return skillsinfoService.updateSkillsBySkills(skillsinfo);
    }

    @DeleteMapping("/del/{siSkillid}")
    @ApiOperation(value = "删除技能", notes = "删除技能")
    public int delectSkillsBySiSerianumber(@PathVariable("siSkillid")String siSerialnumber) {
        return skillsinfoService.delectSkillsBySiSerianumber(siSerialnumber);
    }

    @GetMapping("/selSkillCount/{userId}")
    @ApiOperation(value = "根据用户id查技能个数", notes = "根据用户id查技能个数")

    public int getBySkillIdCount(@PathVariable("userId") String userId) {
        return skillsinfoService.getSkillsByUserCount(userId);
    }

    @GetMapping("/selByUserid/{userId}")
    @ApiOperation(value = "根据用户id查所有技能信息", notes = "根据用户id查所有技能信息")
    public List<Skillsinfo> getByUserId(@PathVariable("userId")String userId) {
        return skillsinfoService.getByUserId(userId);
    }

    @GetMapping("/getAllSkills")
    @ApiOperation(value = "分页查询所有任务",notes = "分页查询所有任务")
    public PageInfo<Skillsinfo> getAllSkills(@RequestParam int state,@RequestParam int pageNum,@RequestParam int pageSize){
        return skillsinfoService.getAllSkills(state);
    }

}
