package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Post;
import cn.qiandao.shengqianyoudao.pojo.reply;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
public interface PostService {

    List<Post> getAllPostByType(String typename);
    Post getPostByType(String postnumber);
    String comment(reply reply, String postnumber);
    PageInfo<Post> getAllPost(int pageNum, int pageSize);
}
