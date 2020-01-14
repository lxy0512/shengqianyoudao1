package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skillcomment;
import cn.qiandao.shengqianyoudao.service.SkillcommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/5 0005 17:11
 **/
@RestController
@CrossOrigin
@RequestMapping("/skillcomment")
@Api(value="/skillcomment",description="技能评价API")
public class SkillcommentController {
    @Autowired
    private SkillcommentService skillcommentService;
    @GetMapping("/selskillcommnents/{skid}")
    @ApiOperation(value = "查询技能评价信息", notes = "查询技能评价信息")
    @ApiImplicitParam(name = "skid", value = "技能编号", required = true, dataType = "string", paramType = "path")
    public List<Skillcomment> selSkillcomment(@PathVariable("skid") String sknumber){
        return skillcommentService.selSkillcomment(sknumber);
    }
}
