package cn.qiandao.shengqianyoudao.service;

/**
 * redis服务接口
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/09/29 16:10
 */
public interface RedisService {
    /**
     * 用户点赞技能
     *
     * @param userId 点赞用户
     * @param skillsId 技能ID
     */
    void likeArticle(String skillsId, String userId);

    /**
     * 取消点赞
     *
     * @param userId 点赞用户
     * @param skillsId 技能ID
     */
    void unlikeArticle(String skillsId, String userId);

}
