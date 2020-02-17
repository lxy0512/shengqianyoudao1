package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;

/**
 * 用户关系表
 * zrf
 */
public interface ISkillRelationService {
    //插入用户信息
    int addUserSkill(String userId, String skillId);
    Skilluserrelationship selUser(String skillsId);
}
