package cn.qiandao.shengqianyoudao.task;

import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;
import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.CollectionrecordsService;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.util.FastjsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 定时落库任务
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/10/08 16:17
 */
@Service
@Slf4j
public class ScheduleTask {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 用户点赞文章key
     */
    @Value("${user.like.article.key}")
    private String USER_LIKE_ARTICLE_KEY;

    @Resource
    private CollectionrecordsService userLikeArticleService;

    @Autowired
    private ISkillRelationService skillRelationService;

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 */2 * * ? ")
    public void redisDataToMySQL() {
        log.info("time:{}，开始执行Redis数据持久化到MySQL任务", LocalDateTime.now().format(formatter));
        Map<String, String> userCountMap = redisTemplate.opsForHash().entries(USER_LIKE_ARTICLE_KEY);
        for (Map.Entry<String, String> entry : userCountMap.entrySet()) {
            String userId = entry.getKey();
            Set<String> skillIdSet = FastjsonUtil.deserializeToSet(entry.getValue(), String.class);
            //2.同步用户喜欢的文章
            synchronizeUserLikeArticle(userId, skillIdSet);
        }
        log.info("time:{}，结束执行Redis数据持久化到MySQL任务", LocalDateTime.now().format(formatter));
    }

    /**
     * 同步用户喜欢的文章
     *
     * @param userId
     * @param skillIdSet
     * */
    private void synchronizeUserLikeArticle(String userId, Set<String> skillIdSet) {
        for (String skillId : skillIdSet) {
            Collectionrecords userLikeArticle = buildUserLikeArticle(skillId, userId);
            Collectionrecords collectionrecords = userLikeArticleService.selectOne(userLikeArticle);
            log.info(collectionrecords + "");
            if (null == userLikeArticleService.selectOne(userLikeArticle)) {
                userLikeArticle.setCorDate(new Date());
                userLikeArticleService.insert(userLikeArticle);
            }
        }
    }

    /**
     * 构造UserLikeArticle对象
     *
     * @param skillId
     * @param userId
     * @return
     * */
    private Collectionrecords buildUserLikeArticle(String skillId, String userId) {
        Collectionrecords userLikeArticle = new Collectionrecords();
        userLikeArticle.setCorSdnumber(skillId);
        userLikeArticle.setCorUsernumber(userId);
        Skilluserrelationship skilluserrelationship = skillRelationService.selUser(skillId);
        userLikeArticle.setCorReleaseusernumber(skilluserrelationship.getSurUsernumber());
        return userLikeArticle;
    }

}
