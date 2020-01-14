package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author lxy
 * @date 2020/1/4 0004 18:46
 **/
public interface SkillsinfoService {
    /**
     * 根据技能编号查询技能信息
     * @param siSerialnumber
     * @return
     */
    Skillsinfo selectBySiSerialnumber(String siSerialnumber);

    /**
     * 查询查询所有的技能信息
     * @return
     */
    List<Skillsinfo> selectAll();

    /**
     * 根据技能类型编号返回技能类型标题
     * @param skillId
     * @return
     */
    String getSiiType(String skillId);

    /**
     * 增加技能
     * @param skillsinfo
     * @return
     */
    int addSkills(Skillsinfo skillsinfo);

    /**
     * 根据技能编号查用户编号
     * @param skillId
     * @return
     */
    Skilluserrelationship getUser(String skillId);

    /**
     * 根据用户编号查技能个数
     * @param UserId
     * @return
     */
    int getSkillsByUserCount(String UserId);

    /**
     * 根据用户编号查技能集合
     * @param UserId
     * @return
     */
    List<Skilluserrelationship> getSkillsByUser(String UserId);


    /**
     * 修改信息
     * @param skillsinfo
     * @return
     */
    int updateSkillsBySkills(Skillsinfo skillsinfo);

    /**
     * 删除信息
     * @param siSerialnumber
     * @return
     */
    int delectSkillsBySiSerianumber(String siSerialnumber);


    /**
     * 根据用户id查所有技能信息
     * @param userId
     * @return
     */
    List<Skillsinfo> getByUserId(String userId);

    PageInfo<Skillsinfo> getAllSkills(int state, int pageNum, int pageSize);

}
