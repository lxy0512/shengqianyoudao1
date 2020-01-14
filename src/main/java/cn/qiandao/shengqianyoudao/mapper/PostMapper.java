package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.Post;
import cn.qiandao.shengqianyoudao.pojo.reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Mapper
public interface PostMapper {

    List<Post> getPostAllByType(@Param("typename") String typename);
    List<reply> getReplyByPost(@Param("postnumber") String postnumber);
    Post getPostByType(@Param("typename") String typename);
    String getNumber();
    int saveReply(@Param("reply") reply reply, @Param("postnumber") String postnumber);
    int updatePostReplies(@Param("postnumber") String postnumber);
    String getName(@Param("aimnumber") String aimnumber);
    List<Post> gethotpost();
}
