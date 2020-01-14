package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.SkilltypeMapper;
import cn.qiandao.shengqianyoudao.pojo.Skilltype;
import cn.qiandao.shengqianyoudao.service.SkilltypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/3 0003 23:43
 **/
@Service
public class SkilltypeServiceImpl implements SkilltypeService {
    @Autowired
    private SkilltypeMapper skilltypeMapper;

    @Override
    public Skilltype selByStNumber(String stNumber){
        Skilltype skilltype = new Skilltype();
        skilltype.setStNumber(stNumber);
        skilltype = skilltypeMapper.selectOne(skilltype);
        if (skilltype == null){
            skilltype.setStContent(stNumber);
            return skilltypeMapper.selectOne(skilltype);
        }
        return skilltype;
    }
    @Override
    public List<Skilltype> getByFamilyAll(String siFamilyId){
        Skilltype skilltype = new Skilltype();
        skilltype.setStFamilynumber(siFamilyId);
        return skilltypeMapper.select(skilltype);
    }

    /**
     * 获取技能的编号
     * @param skillName
     * @return
     */
    @Override
    public String getSkillId(String skillName) {
        Skilltype skilltype = new Skilltype();
        skilltype.setStContent(skillName);
        Skilltype skillResult = skilltypeMapper.selectOne(skilltype);
        return skillResult.getStNumber();
    }

    /*@Override
    public List<Skilltype> getBySonAll(String siFamilyId) {
        return null;
    }*/



}
