package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.SkillRelationMapper;
import cn.qiandao.shengqianyoudao.mapper.SkillsinfoMapper;
import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import cn.qiandao.shengqianyoudao.pojo.Skilltype;
import cn.qiandao.shengqianyoudao.pojo.Skilluserrelationship;
import cn.qiandao.shengqianyoudao.service.SkillorderService;
import cn.qiandao.shengqianyoudao.service.SkillsinfoService;
import cn.qiandao.shengqianyoudao.service.SkilltypeService;
import cn.qiandao.shengqianyoudao.service.UserService;
import cn.qiandao.shengqianyoudao.util.IDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxy
 * @date 2020/1/3 0003 23:09
 **/
@Service
public class SkillsinfoServiceImpl implements SkillsinfoService {
    private Logger log = LoggerFactory.getLogger(SkillsinfoServiceImpl.class);
    @Autowired
    private SkillsinfoMapper skillsinfoMapper;
    @Autowired
    private SkilltypeService skilltypeService;
    @Autowired
    private SkillRelationMapper skillRelationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SkillorderService skillorderService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    @Transactional
    public Skillsinfo selectBySiSerialnumber(String siSerialnumber){
        Skillsinfo s = null;
        //(Skillsinfo) redisTemplate.opsForValue().get(siSerialnumber);
        if (s == null){
            s = new Skillsinfo();
            s.setSiSerialnumber(siSerialnumber);
            s = skillsinfoMapper.selectOne(s);
            log.info("技能：" + s);
            s.setSiType(getSiiType(s.getSiType()));
            if (s.getSiImg().indexOf(',')!=-1){
                String[] imgList = s.getSiImg().split(",");
                s.setSiImgages(imgList);
                s.setSiImg(imgList[0]);
            }
            Skilluserrelationship user = getUser(siSerialnumber);
            log.info(user.getSurUsernumber());
            if (user != null){
                s.setU(userService.findById(user.getSurUsernumber()));
            }
            s.setSingularization(skillorderService.selectBySkillIdCount(siSerialnumber));
            s.setMorningstarRating(5);
            //redisTemplate.opsForValue().set(siSerialnumber,s);
        }
        log.info("修改技能：" + s);
        return s;
    }

    @Override
    public List<Skillsinfo> selectAll() {
        List<Skillsinfo> skillsinfos = skillsinfoMapper.selectAll();
        for (Skillsinfo si : skillsinfos) {
            si.setSiImg(si.getSiImg().split(",")[0]);
            //Skillsinfo newSkills = new Skillsinfo();
            //newSkills = (Skillsinfo) redisTemplate.opsForValue().get(si.getSiSerialnumber());
            //if (newSkills == null){
            Skilluserrelationship user = getUser(si.getSiSerialnumber());
            si.setU(userService.findById(user.getSurUsernumber()));
            //redisTemplate.opsForValue().set(si.getSiSerialnumber(),newSkills);
            //}
        }
        return skillsinfos;
    }

    @Override
    public String getSiiType(String skillId){
        Skilltype skilltype1 = skilltypeService.selByStNumber(skillId);
        Skilltype skilltype2 = skilltypeService.selByStNumber(skilltype1.getStFamilynumber());
        String type = skilltype2.getStContent() + "-" + skilltype1.getStContent();
        return type;
    }

    @Override
    public int addSkills(Skillsinfo skillsinfo) {
        String skid = (String) redisTemplate.opsForValue().get("技能");
        log.info("旧值是" + skid);
        String jb = IDUtil.getNewEquipmentNo("jx", skid);
        log.info("新值是" + skid);
        redisTemplate.opsForValue().set("技能",jb);
        skillsinfo.setSiSerialnumber(jb);
        return skillsinfoMapper.insert(skillsinfo);
    }

    @Override
    public Skilluserrelationship getUser(String skillId) {
        Skilluserrelationship sur = new Skilluserrelationship();
        sur.setSurSkillnumber(skillId);
        return skillRelationMapper.selectOne(sur);
    }

    /**
     * 根据用户编号查技能个数
     *
     * @param UserId
     * @return
     */
    @Override
    public int getSkillsByUserCount(String UserId) {
        Skilluserrelationship sur = new Skilluserrelationship();
        sur.setSurUsernumber(UserId);
        return skillRelationMapper.selectCount(sur);
    }

    /**
     * 根据用户编号查技能集合
     *
     * @param UserId
     * @return
     */
    @Override
    public List<Skilluserrelationship> getSkillsByUser(String UserId) {
        Skilluserrelationship sur = new Skilluserrelationship();
        sur.setSurUsernumber(UserId);
        return skillRelationMapper.select(sur);
    }

    /**
     * 修改信息
     *
     * @param skillsinfo
     * @return
     */
    @Override
    public int updateSkillsBySkills(Skillsinfo skillsinfo) {
        return skillsinfoMapper.updateByPrimaryKeySelective(skillsinfo);
    }

    /**
     * 删除信息
     *
     * @param siSerialnumber
     * @return
     */
    @Override
    public int delectSkillsBySiSerianumber(String siSerialnumber) {
        Skillsinfo skillsinfo = new Skillsinfo();
        skillsinfo.setSiSerialnumber(siSerialnumber);
        return skillsinfoMapper.delete(skillsinfo);
    }

    /**
     * 根据用户id查所有技能信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<Skillsinfo> getByUserId(String userId) {
        List<Skillsinfo> list = new ArrayList<>();
        List<Skilluserrelationship> skillsByUser = getSkillsByUser(userId);
        for (Skilluserrelationship sui:skillsByUser){
            Skillsinfo skillsinfo = selectBySiSerialnumber(sui.getSurSkillnumber());
            list.add(skillsinfo);
        }
        return list;
    }

    @Override
    public PageInfo<Skillsinfo> getAllSkills(int state) {
        PageHelper.startPage(1,4);
        Skillsinfo skillsinfo = new Skillsinfo();
        skillsinfo.setSiState(0);
        List<Skillsinfo> select = skillsinfoMapper.select(skillsinfo);
        List<Skillsinfo> select1 = PageHelper.startPage(1,4);
        List<Skillsinfo> select2 = PageHelper.startPage(1,4);
        for (Skillsinfo si : select) {
            Skilltype skilltype1 = skilltypeService.selByStNumber(si.getSiType());
            si.setSiType(getSiiType(si.getSiType()));
            if (si.getSiImg().indexOf(',')!=-1){
                String[] imgList = si.getSiImg().split(",");
                si.setSiImgages(imgList);
                si.setSiImg(imgList[0]);
            }
            Skilluserrelationship user = getUser(si.getSiSerialnumber());
            if (user != null){
                si.setU(userService.findById(user.getSurUsernumber()));
            }
            si.setSingularization(skillorderService.selectBySkillIdCount(si.getSiSerialnumber()));
            si.setMorningstarRating(5);
            log.info("技能用户：" + si);

            if (skilltype1.getStFamilynumber().equals("jn0016")){
                select1.add(si);
            }else {
                select2.add(si);
            }
        }
        PageInfo<Skillsinfo> pageInfo = null;
        if(state == 1){
            pageInfo = new PageInfo<Skillsinfo>(select1,4);
            //return select1;
        }else {
            pageInfo = new PageInfo<Skillsinfo>(select2);
            //return select2;
        }
        return pageInfo;
    }
}
