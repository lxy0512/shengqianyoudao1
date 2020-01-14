package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.PostMapper;
import cn.qiandao.shengqianyoudao.pojo.Post;
import cn.qiandao.shengqianyoudao.pojo.reply;
import cn.qiandao.shengqianyoudao.service.PostService;
import cn.qiandao.shengqianyoudao.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper pm;

    /**
     * 查看某个类型的帖子
     * @param typename
     * @return
     */
    @Override
    public List<Post> getAllPostByType(String typename){
        List<Post> l = pm.getPostAllByType(typename);
        return l;
    }

    /**
     * 查看某个帖子
     * @param postnumber
     * @return
     */
    @Override
    public Post getPostByType(String postnumber){
        Post post = pm.getPostByType(postnumber);
        post.setReply(pm.getReplyByPost(postnumber));
        for (reply r : post.getReply()) {
            if (r.getAimnumber().substring(0,2).equals("hf")){
               r.setAimusername(pm.getName(r.getAimnumber()));
            }
        }
        return post;
    }

    public List<Post> gethotpost(){
        return pm.gethotpost();
    }

    /**
     * 发布评论
     * @param reply
     * @param postnumber
     * @return
     */
    @Override
    @Transactional
    public String comment(reply reply,String postnumber){
        reply.setNumber(IDUtil.getNewEquipmentNo("hf",pm.getNumber().substring(3)));
        if (reply.getAimnumber().substring(0,2).equals("tz")){
            reply.setStand(1);
        }else {
            reply.setStand(2);
        }
        if (pm.saveReply(reply,postnumber) > 0){
            if(pm.updatePostReplies(postnumber) > 1){
                return "发布成功";
            }
        }
        return "发布失败";
    }

}
