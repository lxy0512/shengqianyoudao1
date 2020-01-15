package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.PostInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PostInfoMapper extends Mapper<PostInfo> {
    @Select("SELECT MAX(pi_number) from postinfo")
    String getPostInfoMax();
}
