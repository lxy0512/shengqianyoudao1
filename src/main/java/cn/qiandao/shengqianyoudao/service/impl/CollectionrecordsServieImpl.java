package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.CollectionrecordsMapper;
import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;
import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import cn.qiandao.shengqianyoudao.service.CollectionrecordsService;
import cn.qiandao.shengqianyoudao.service.SkillsinfoService;
import cn.qiandao.shengqianyoudao.util.FastjsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lxy
 * @date 2020/2/5 0005 16:52
 **/
@Service
public class CollectionrecordsServieImpl implements CollectionrecordsService {

    @Autowired
    private CollectionrecordsMapper collectionrecordsMapper;

    @Value("${user.like.article.key}")
    private String USER_LIKE_ARTICLE_KEY;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkillsinfoService skillsinfoService;

    @Override
    public Collectionrecords selectOne(Collectionrecords collectionrecords) {
        return collectionrecordsMapper.selectOne(collectionrecords);
    }

    @Override
    public int insert(Collectionrecords collectionrecords) {
        return collectionrecordsMapper.insert(collectionrecords);
    }

    @Override
    public List<Skillsinfo> selectAll(String userId) {
        List<Skillsinfo> list = new ArrayList<>();
        String userLikeResult = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, String.valueOf(userId));
        Set<String> articleIdSet = FastjsonUtil.deserializeToSet(userLikeResult, String.class);
        if (articleIdSet == null){
            return null;
        }
        for (String skillId : articleIdSet){
            Skillsinfo skillsinfo = skillsinfoService.selectBySiSerialnumber(skillId);
            list.add(skillsinfo);
        }
        return list;
    }
}
