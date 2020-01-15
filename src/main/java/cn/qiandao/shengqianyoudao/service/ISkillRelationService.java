package cn.qiandao.shengqianyoudao.service;

/**
 * 用户关系表
 * zrf
 */
public interface ISkillRelationService {
    //插入用户信息
    int addUserSkill(String userId, String skillId);
}
