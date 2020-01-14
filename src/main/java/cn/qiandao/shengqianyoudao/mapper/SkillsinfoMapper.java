package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author fyy
 * @date 2020/1/3 0003 23:07
 **/
@Repository
public interface SkillsinfoMapper extends Mapper<Skillsinfo> {
    @Select("select MAX(si_serialnumber) from skillsinfo")
    String getMaxSkillId();
}
