package cn.qiandao.shengqianyoudao.service;


import cn.qiandao.shengqianyoudao.pojo.Skillcomment;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/5 0005 17:08
 **/
public interface SkillcommentService {
    List<Skillcomment> selSkillcomment(String sknumber);
}
