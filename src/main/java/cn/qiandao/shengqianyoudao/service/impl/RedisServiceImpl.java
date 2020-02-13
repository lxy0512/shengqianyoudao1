package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.enums.ErrorCodeEnum;
import cn.qiandao.shengqianyoudao.exception.CustomException;
import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;
import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.CollectionrecordsService;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.service.RedisService;
import cn.qiandao.shengqianyoudao.util.FastjsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis服务实现类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/09/29 16:05
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    /**
     * 用户点赞文章key
     */
    @Value("${user.like.article.key}")
    private String USER_LIKE_ARTICLE_KEY;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private ISkillRelationService skillRelationService;

    /**
     * 指定序列化方式
     */
    @PostConstruct
    public void init() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
    }

    /**
     * 用户点赞技能
     *
     * @param skillsId 技能ID
     * @param userId 点赞用户
     */
    @Override
    public void likeArticle(String skillsId, String userId) {
        //参数验证
        validateParam(skillsId,userId);

        log.info("点赞数据存入redis开始，skillsId:{}，userId:{}", skillsId, userId);

        //只有未点赞的用户才可以进行点赞
        likeArticleLogicValidate(skillsId, userId);
        synchronized (this) {
            //用户喜欢的技能+1
            String userLikeResult = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, String.valueOf(userId));
            Set<String> articleIdSet = userLikeResult == null ? new HashSet<>() : FastjsonUtil.deserializeToSet(userLikeResult, String.class);
            articleIdSet.add(skillsId);
            redisTemplate.opsForHash().put(USER_LIKE_ARTICLE_KEY, String.valueOf(userId), FastjsonUtil.serialize(articleIdSet));
            log.info("点赞数据存入redis结束，skillsId:{}，userId:{}", skillsId, userId);
        }
    }

    /**
     * 取消点赞
     *
     * @param skillsId 技能ID
     * @param userId 点赞用户
     */
    @Override
    public void unlikeArticle(String skillsId, String userId) {
        //参数校验
        validateParam(skillsId, skillsId);

        log.info("取消点赞数据存入redis开始，skillsId:{}，userId:{}", skillsId, userId);
        synchronized (this) {
            //只有点赞的用户才可以取消点赞
            unlikeArticleLogicValidate(skillsId,userId);

            //用户喜欢的文章-1
            String userLikeResult = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, String.valueOf(userId));
            Set<String> articleIdSet = FastjsonUtil.deserializeToSet(userLikeResult, String.class);
            articleIdSet.remove(skillsId);
            redisTemplate.opsForHash().put(USER_LIKE_ARTICLE_KEY, String.valueOf(userId), FastjsonUtil.serialize(articleIdSet));
        }

        log.info("取消点赞数据存入redis结束，skillsId:{}，userId:{}", skillsId, userId);
    }

    /**
     * 入参验证
     *
     * @param params
     * @throws CustomException
     */
    private void validateParam(String... params) {
        for (String param : params) {
            if (null == param) {
                log.error("入参存在null值");
                throw new CustomException(ErrorCodeEnum.Param_can_not_null);
            }
        }
    }

    /**
     * 点赞文章逻辑校验
     *
     * @throws
     */
    private void likeArticleLogicValidate(String skillsId, String userId) {
        Set<String> articleIdSet = null;
        Skilluserrelationship skilluserrelationship = skillRelationService.selUser(skillsId);
        if(skilluserrelationship.getSurUsernumber().equals(userId)){
            log.error("该文章是当前用户的，不需要点赞，skillsId:{}，userId:{}", skillsId, userId);
            throw new CustomException(ErrorCodeEnum.Skill_belong_user);
        }else{
            articleIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, String.valueOf(userId)), String.class);
        }
        if (articleIdSet == null) {
            return;
        } else {
            if (articleIdSet.contains(skillsId)) {
                log.error("该文章已被当前用户点赞，重复点赞，skillsId:{}，userId:{}", skillsId, userId);
                throw new CustomException(ErrorCodeEnum.Like_article_is_exist);
            }
        }
    }

    /**
     * 取消点赞逻辑校验
     */
    private void unlikeArticleLogicValidate(String skillsId, String userId) {
        Set<String> articleIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, String.valueOf(userId)), String.class);
        if (articleIdSet == null || !articleIdSet.contains(skillsId)) {
            log.error("该文章未被当前用户点赞，不可以进行取消点赞操作skillsId:{}，userId:{}", skillsId, userId);
            throw new CustomException(ErrorCodeEnum.Unlike_article_not_exist);
        }
    }
}
