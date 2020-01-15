package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.GameSkillsInfo;
import cn.qiandao.shengqianyoudao.pojo.PostPojo;
import cn.qiandao.shengqianyoudao.pojo.PublishGameSkill;

import java.util.List;
import java.util.Map;

public interface PublishSkillService {
    /**
     * skillinfo
     * @return
     */
    //获取db最大技能编号
    String getMaxskill();
    //插入技能
    String pubSkill(Map InsertSkillinfo);
    //新建技能编号
    String makeSkillID();
    /**
     * skilltype
     * @param skillName
     * @return
     */
    String getSkillId(String skillName);
    //查询等级id
    String getGradId(String gradStringId, String gameName);
    //查询游戏区服
    String getDistrict(String districtString, String gameName);
    /**
     * @param publishGameSkill
     * @return
     */
    //发布游戏技能
    String modifyContent(PublishGameSkill publishGameSkill);
    List getPostFamliy(String postStringId);
    /**
     * postInfo
     */
    String pubPost(Map postInfo);
    String makePostId();
    List getSkill(int skill);
    List getprofessionalSkill(int skill);
}
