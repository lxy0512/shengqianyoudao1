package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Post;
import cn.qiandao.shengqianyoudao.pojo.reply;
import cn.qiandao.shengqianyoudao.service.impl.PostServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl psi;

    /**
     * 后台查看所有帖子
     */
    @RequestMapping("/get/{pageNum}/{pageSize}")
    public PageInfo<Post> getAllPost(@PathVariable("pageNum")int pageNum, @PathVariable("pageSize")int pageSize){
        return psi.getAllPost(pageNum,pageSize);
    }
    /**
     * 获取某个类型的全部帖子
     * @param typename
     * @return
     */
    @RequestMapping("/allbytype/{typename}")
    public List<Post> getAllPostByType(@PathVariable("typename")String typename){
        return psi.getAllPostByType(typename);
    }

    /**
     * 获取某个帖子的全部信息
     * @param postnumber
     * @return
     */
    @RequestMapping("/getpost/{postnumber}")
    public Post getPost(@PathVariable("postnumber")String postnumber){
        return psi.getPostByType(postnumber);
    }

    /**
     * 获取热门帖子
     * @return
     */
    @RequestMapping("/gethotpost/")
    public List<Post> gethotpost(){
        return psi.gethotpost();
    }

    /**
     * 发布评论
     * @param reply
     * @return
     */
    @RequestMapping("/comment/{postnumber}")
    public String comment(@RequestBody reply reply,@PathVariable("postnumber") String postnumber){
        return  psi.comment(reply,postnumber);
    }
}
