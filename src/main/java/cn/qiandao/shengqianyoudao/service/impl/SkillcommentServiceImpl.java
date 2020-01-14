package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.SkillcommentMapper;
import cn.qiandao.shengqianyoudao.pojo.Skillcomment;
import cn.qiandao.shengqianyoudao.service.SkillcommentService;
import cn.qiandao.shengqianyoudao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/5 0005 17:09
 **/
@Service
public class SkillcommentServiceImpl implements SkillcommentService {

    @Autowired
    private SkillcommentMapper skillcommentMapper;
    @Autowired
    private UserService userService;
    @Override
    public List<Skillcomment> selSkillcomment(String sknumber) {
        Skillcomment skillcomment = new Skillcomment();
        skillcomment.setScSkillnumber(sknumber);
        List<Skillcomment> select = skillcommentMapper.select(skillcomment);
        for (Skillcomment skillcomment1:select){
            skillcomment1.setUser(userService.findById(skillcomment1.getScUsernumber()));
        }
        return select;
    }
}
