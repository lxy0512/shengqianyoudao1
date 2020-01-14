package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Virtualcurrencyrecords;
import cn.qiandao.shengqianyoudao.service.VirtualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/4 0004 18:47
 **/
@RestController
@CrossOrigin
@RequestMapping("/Virtual")
@Api(value="/Virtual",description="金币明细API")
public class VirtualController {

    @Autowired
    private VirtualService virtualService;

    @ApiOperation(value = "查询金币流水信息", notes = "查询金币流水信息")
    @ApiImplicitParam(name = "cuid", value = "用户编号", required = true, dataType = "string", paramType = "path")
    @GetMapping("/sel/{vuid}")
    public List<Virtualcurrencyrecords> getListVirtual(@PathVariable("vuid") String vuid){
        return virtualService.getListVirtual(vuid);
    }

    @ApiOperation(value = "添加金币流水信息", notes = "添加金币流水信息")
    @PostMapping("/add")
    public int addVirtualcords(@RequestBody Virtualcurrencyrecords v){
        return virtualService.addVirtualcords(v);
    }

    @ApiOperation(value = "删除金币流水信息", notes = "删除金币流水信息")
    @ApiImplicitParam(name = "vid", value = "金币编号", required = true, dataType = "string", paramType = "path")
    @DeleteMapping("/del/{vid}")
    public int delVirtualcords(@PathVariable("vid") String vid){
        return virtualService.delVirtualcords(vid);
    }

    @ApiOperation(value = "更新金币流水信息", notes = "更新金币流水信息")
    @PutMapping("/update")
    public int updateVirtualcords(@RequestBody Virtualcurrencyrecords v){
        return virtualService.updateVirtualcords(v);
    }

    @ApiOperation(value = "出售金币", notes = "出售金币")
    @GetMapping("/sellVc/{rate}/{vc}/{uid}")
    public String sellVc(@PathVariable("rate")double rate,@PathVariable("vc")double vc,@PathVariable("uid")String uid){
        return virtualService.sellByRate(rate,vc,uid);
    }
}
