package cn.qiandao.shengqianyoudao.controller;


import cn.qiandao.shengqianyoudao.pojo.Taskinfo;
import cn.qiandao.shengqianyoudao.pojo.Userinfo;
import cn.qiandao.shengqianyoudao.service.TaskinfoService;
import cn.qiandao.shengqianyoudao.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 任务详情页展示
 * @author fyy
 * @date 2019/12/28 18:16
 **/
@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskinfoController {

    private static final Logger log = LoggerFactory.getLogger(TaskinfoController.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TaskinfoService taskinfoService;

    @ApiImplicitParam(name = "userid", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @GetMapping("/getTask/{taskid}")
    @ApiOperation(value = "根据任务编号id查询详情",notes = "根据任务id查询详情")
    public Taskinfo selectTask1(@PathVariable("taskid") String taskid){
        Taskinfo taskinfo = taskinfoService.selectTask(taskid);
        return taskinfo;
    }

    @GetMapping("/getTypeTask/{type}")
    @ApiOperation(value = "根据任务类型查询任务集合",notes = "根据任务类型查询任务集合")
    public List<Taskinfo> selectType(@PathVariable("type") Integer type){
        List<Taskinfo> taskinfos = taskinfoService.selectAllTask(type);
        return taskinfos;
    }

    @GetMapping("/selectUpTask/{type}/{sort}")
    @ApiOperation(value = "任务排序",notes = "任务排序")
    public List<Taskinfo> selectUp(@PathVariable("type") String type,@PathVariable("sort") String sort){
        List<Taskinfo> taskinfos = taskinfoService.selectByExample(type, sort);
        return taskinfos;
    }

    @GetMapping("/selectTasks/{user}")
    @ApiOperation(value = "获取用户发布的任务",notes = "获取用户发布的任务")
    public List<Taskinfo> selectTasks(@PathVariable("user")String user){
        
        return taskinfoService.selectTasks(user);
    }

    @GetMapping("/getTackCount/{user}/{state}")
    @ApiOperation(value = "查询任务的数量（不同状态）",notes = "查询任务的数量（不同状态）")
    public int getTackCount(@PathVariable("user")String user,@PathVariable("state") int state){
        return taskinfoService.getTackCount(user,state);
    }

    @GetMapping("/getTaskState/{user}/{state}")
    @ApiOperation(value = "查询任务后展示(不同状态)",notes = "获取用户发布的任务")
    public List<Taskinfo> getTaskState(@PathVariable("user")String user,@PathVariable("state") int state){
        return taskinfoService.getTaskState(user,state);
    }

    @GetMapping("/acceptTask/{taskid}/{tiPeoplelimit}")
    @ApiOperation(value = "接受任务)",notes = "接受任务")
    public int acceptTask(@PathVariable("tiPeoplelimit") int tiPeoplelimit,@PathVariable("taskid") String taskid){
        return taskinfoService.acceptTask(tiPeoplelimit,taskid);
    }

    @PutMapping("/updateTask/{taskid}/{state}")
    @ApiOperation(value = "后台修改任务的state",notes = "后台修改任务的state")
    public int updateTask(@PathVariable("taskid") String taskid,@PathVariable("state") int state){
        return taskinfoService.updateTask(taskid,state);
    }

    @GetMapping("/getAllTask")
    @ApiOperation(value = "分页查询所有任务",notes = "分页查询所有任务")
    public PageInfo<Taskinfo> getAllTask(@RequestParam int state,@RequestParam int pageNum,@RequestParam  int pageSize){
        return taskinfoService.getAllTask(state,pageNum,pageSize);
    }

    @DeleteMapping("/delTask")
    @ApiOperation(value = "通过任务编号删除任务",notes = "通过任务编号删除任务")
    public int delTask(String taskid){
        return taskinfoService.delTask(taskid);
    }
}
