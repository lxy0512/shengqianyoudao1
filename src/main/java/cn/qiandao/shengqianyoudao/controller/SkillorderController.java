package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Skillorder;
import cn.qiandao.shengqianyoudao.service.SkillorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fyy
 * @date 2020/1/9 11:54
 **/
@Slf4j
@CrossOrigin
@Api(value = "技能订单",tags = "获取技能订单信息的控制器分组",description = "描述：技能订单")
@RestController
@RequestMapping("/skillorder")
public class SkillorderController {
    @Autowired
    private SkillorderService skillorderService;

    @ApiOperation(value = "添加订单信息,返回订单id")
    @PostMapping("/Order")
    public Long addSkillOrder(@RequestBody Skillorder skillorder){
        System.out.println(skillorder);
        Long order = skillorderService.addKillOrder(skillorder);
        return order;
    }

    @ApiOperation(value = "修改订单状态")
    @PutMapping("/updateOrder/{orderId}/{state}")
    public int Order(@PathVariable("orderId") Long orderId,@PathVariable("state") int state){
        return skillorderService.updateOrder(orderId,state);
    }

    @ApiOperation(value = "查询订单信息")
    @GetMapping("/selectOrder/{orderId}")
    public  Skillorder killOrder(@PathVariable("orderId") Long orderId){
        return skillorderService.selectOrder(orderId);
    }

    @ApiOperation(value = "查询用户接收的技能订单，用户模块")
    @GetMapping("/userorders/{userId}")
    public List<Skillorder> getuserOrders(@PathVariable("userId") String user){
        return skillorderService.selectUserOrder(user);
    }

    @ApiOperation(value = "查询用户购买的技能，用户模块")
    @GetMapping("/byorders/{userId}")
    public List<Skillorder> getByOrders(@PathVariable("userId") String user){
        return skillorderService.selectByOrder(user);
    }
}

