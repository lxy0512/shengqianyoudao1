package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Skilltype;

import java.util.List;

/**
 * @author lxy
 * @date 2020/1/4 0004 18:46
 **/
public interface SkilltypeService {
    Skilltype selByStNumber(String stNumber);
    List<Skilltype> getByFamilyAll(String siFamilyId);
    //List<Skilltype> getBySonAll(String siFamilyId);
    String getSkillId(String skillName);
}
