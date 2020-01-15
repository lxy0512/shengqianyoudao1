package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.Taskinfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


public interface TaskinfoMapper extends Mapper<Taskinfo> {
    @Select("SELECT MAX(ti_tasknumber) from taskinfo")
    String getMaxtasknumber();
}