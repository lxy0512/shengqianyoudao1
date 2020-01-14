package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Cashrecords;
import cn.qiandao.shengqianyoudao.service.CashrecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/4 0004 17:25
 **/
@RestController
@RequestMapping("/cashrecord")
@Api(value="/cashrecord",description="现金明细API")
@CrossOrigin
public class CashrecordsController {
    @Autowired
    private CashrecordsService cashrecordsService;

    @ApiImplicitParam(name = "cuid", value = "用户编号", required = true, dataType = "string", paramType = "path")
    @GetMapping("/sel/{cuid}")
    public List<Cashrecords> getListCashre(@PathVariable("cuid") String cuid){
        return cashrecordsService.getCashreByCuId(cuid);
    }

    @ApiOperation(value = "添加现金流水信息", notes = "添加现金流水信息")
    @PostMapping("/add")
    public int addCashrecords(@RequestBody Cashrecords cashrecords){
        return cashrecordsService.addCashrecords(cashrecords);
    }

    @ApiOperation(value = "删除现金流水信息", notes = "删除现金流水信息")
    @ApiImplicitParam(name = "vid", value = "现金编号", required = true, dataType = "string", paramType = "path")
    @DeleteMapping("/del/{vid}")
    public int delCashrecords(@PathVariable("vid") String vid){
        return cashrecordsService.delCashrecords(vid);
    }

    @ApiOperation(value = "更新现金流水信息", notes = "更新现金流水信息")
    @PutMapping("/update")
    public int updateCashrecords(@RequestBody Cashrecords cashrecords){
        return cashrecordsService.updateCashrecords(cashrecords);
    }

}
