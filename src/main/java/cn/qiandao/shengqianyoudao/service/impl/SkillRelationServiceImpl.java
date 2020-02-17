package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.SkillRelationMapper;
import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.util.DateTimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SkillRelationServiceImpl implements ISkillRelationService {

    @Autowired
    private SkillRelationMapper skillRelationMapper;
    @Override
    public int addUserSkill(String userId, String skillId) {
        SimpleDateFormat current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式"yyyy-MM-dd HH:mm:ss"
        System.out.println(current.format(new Date()));// new Date()为获取当前系统时间
        Skilluserrelationship skilluserrelationship = new Skilluserrelationship();
        skilluserrelationship.setSurId(0);
        skilluserrelationship.setSurUsernumber("yh10009");
        skilluserrelationship.setSurSkillnumber("jx10009");
        skilluserrelationship.setSurDate(DateTimeUntil.strToDate(current.format(new Date())));
        System.out.println(skilluserrelationship.toString());
        int insert = skillRelationMapper.insert(skilluserrelationship);
        skilluserrelationship.setSurSkillnumber("jx100002");
        Skilluserrelationship skilluserrelationship2 = skillRelationMapper.selectOne(skilluserrelationship);
        System.out.println("time:"+skilluserrelationship2);
        List<Skilluserrelationship> skilluserrelationships = skillRelationMapper.selectAll();
//        skilluserrelationships.forEach(
//                skilluserrelationship1 -> System.out.println(skilluserrelationship1.getSurDate())
//        );

        for (Skilluserrelationship skilluserrelationship1 : skilluserrelationships) {
            System.out.println(DateTimeUntil.dateToStr(skilluserrelationship1.getSurDate()));
        }
        System.out.println(insert+"----");
        return 0;
    }

    @Override
    public Skilluserrelationship selUser(String skillsId) {
        Skilluserrelationship skilluserrelationship = new Skilluserrelationship();
        skilluserrelationship.setSurSkillnumber(skillsId);
        return skillRelationMapper.selectOne(skilluserrelationship);
    }
}
