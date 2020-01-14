package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import tk.mybatis.mapper.common.Mapper;

public interface SkillRelationMapper extends Mapper<Skilluserrelationship> {
    void selectOne();
}
