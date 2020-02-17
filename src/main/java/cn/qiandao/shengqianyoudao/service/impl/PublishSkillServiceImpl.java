package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.*;
import cn.qiandao.shengqianyoudao.pojo.*;
import cn.qiandao.shengqianyoudao.service.IImagesService;
import cn.qiandao.shengqianyoudao.service.ISkillRelationService;
import cn.qiandao.shengqianyoudao.service.PublishSkillService;
import cn.qiandao.shengqianyoudao.service.SkilltypeService;
import cn.qiandao.shengqianyoudao.util.CheckSensitiveWords;
import cn.qiandao.shengqianyoudao.util.DateTimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.NewThreadAction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PublishSkillServiceImpl implements PublishSkillService {
    @Autowired
    private SkillsinfoMapper skillsinfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CheckSensitiveWords checkSensitiveWords;
    @Autowired
    private ISkillRelationService iSkillRelationService;
    @Autowired
    private SkilltypeMapper skilltypeMapper;
    @Autowired
    private GameSkillsInfoMapper gameSkillsInfoMapper;
    @Autowired
    private SkilltypeService skilltypeService;
    @Autowired
    private IImagesService iImagesService;
    @Autowired
    private PostInfoMapper postInfoMapper;
    /**
     * 获取数据库最大技能id
     * @return
     */
    @Override
    public String getMaxskill() {
        String maxSkill = skillsinfoMapper.getMaxSkillId();
        redisTemplate.opsForValue().set("maxSkill",maxSkill);
        System.out.println("最大技能id"+maxSkill);
        return maxSkill;
    }

    /**
     * 发布技能
     * @param InsertSkillinfo
     * @return
     */
    @Override
    public String pubSkill(Map InsertSkillinfo) {
        String siTitle = InsertSkillinfo.get("siTitle").toString();
        System.out.println("siTitle:"+siTitle);
        String siDescribe = InsertSkillinfo.get("siDescribe").toString();
        System.out.println("siDescribe:"+siDescribe);
        boolean checkTitle = checkSensitiveWords.checkWorks(siTitle);
        System.out.println(checkTitle + "================");
        boolean checkDescribe = checkSensitiveWords.checkWorks(siDescribe);
        System.out.println(checkDescribe);
        if (checkTitle == true && checkDescribe == true){
            BigDecimal bigDecimal = new BigDecimal(InsertSkillinfo.get("siMoney").toString());
            Skillsinfo skillsinfo = new Skillsinfo();
            System.out.println(makeSkillID());
            skillsinfo.setSiSerialnumber(makeSkillID());
            skillsinfo.setSiTitle(InsertSkillinfo.get("siTitle").toString());
            skillsinfo.setSiType(getSkillId(InsertSkillinfo.get("siType").toString()));
            skillsinfo.setSiMoney(bigDecimal);
            skillsinfo.setSiDescribe(InsertSkillinfo.get("siDescribe").toString());
            skillsinfo.setSiDuration(Integer.parseInt(InsertSkillinfo.get("siDuration").toString()));
            skillsinfo.setSiDate(InsertSkillinfo.get("siDate").toString());
            skillsinfo.setSiImg(InsertSkillinfo.get("siImg").toString());
            skillsinfo.setSiAuthority(0);
            skillsinfo.setSiModifynumber(Integer.parseInt(InsertSkillinfo.get("siModifynumber").toString()));
            skillsinfo.setSiState(0);
            skillsinfo.setSiTostate(2);
            skillsinfo.setSiGrade(0);
            int insertResult = skillsinfoMapper.insert(skillsinfo);
            System.out.println("向数据库插入技能："+insertResult);
            System.out.println("技能信息"+skillsinfo);
            int userResult = iSkillRelationService.addUserSkill(InsertSkillinfo.get("userId").toString(), skillsinfo.getSiSerialnumber());
            System.out.println("用户技能表插入数据"+userResult);
            redisTemplate.opsForValue().set("maxSkill",skillsinfo.getSiSerialnumber());
            System.out.println("skillid存入redis"+redisTemplate.opsForValue().get("maxSkill"));
            if (insertResult == 0 && userResult == 0){
                return "未成功插入数据库";
            }
            return "技能填写完成 进入审核状态";
        }else {
            return "技能填写失败";
        }
    }

    /**
     * 生成新的技能id
     * @return
     */
    @Override
    public String makeSkillID() {
        String maxskilString = "";
        Boolean maxSkill = redisTemplate.hasKey("maxSkill");
        if (maxSkill == false){
            maxskilString = getMaxskill();
        }else {
            maxskilString = (String) redisTemplate.opsForValue().get("maxSkill");
        }

        System.out.println(maxskilString+"---skill----");
        System.out.println(maxskilString);
        System.out.println(maxskilString.substring(2));
        int numberSkillid = (Integer.parseInt(maxskilString.substring(2)))+1;
        System.out.println(maxskilString.substring(2)+"=========");
        maxskilString = "jx"+String.format("%06d",numberSkillid);
        System.out.println("加1后的技能id："+maxskilString);
        return maxskilString;
    }

    /**
     * 根据技能名字查id
     * @param skillName
     * @return
     */
    @Override
    public String getSkillId(String skillName) {
        System.out.println(skillName);
        Skilltype skilltype = new Skilltype();
        skilltype.setStContent(skillName);
        Skilltype skillResult = skilltypeMapper.selectOne(skilltype);
        System.out.println(skillName);
        String stNumber = skillResult.getStNumber();
        return stNumber;
    }

    /**
     * 根据等级名称查id
     * @param gradStringId
     * @return
     */
    @Override
    public String getGradId(String gradStringId,String gameName) {
        Skilltype skilltype = new Skilltype();
        skilltype.setStContent(gameName);
        Skilltype getGamaid = skilltypeMapper.selectOne(skilltype);
        System.out.println("getGamaid"+getGamaid);
        skilltype.setStContent(gradStringId);
        skilltype.setStFamilynumber(getGamaid.getStNumber()+"-2");
        String stNumber = skilltypeMapper.selectOne(skilltype).getStNumber();
        System.out.println("stNumber"+stNumber);
        return stNumber;
    }

    /**
     *根据游戏区服名称查id
     * @param districtString
     * @return
     */
    @Override
    public String getDistrict(String districtString,String gameName) {
        Skilltype skilltype = new Skilltype();
        skilltype.setStContent(gameName);
        Skilltype districtId = skilltypeMapper.selectOne(skilltype);
        System.out.println(districtId);
        skilltype.setStContent(districtString);
        skilltype.setStFamilynumber(districtId.getStNumber()+"-3");
        System.out.println(districtId+"-3");
        Skilltype skilltype1 = skilltypeMapper.selectOne(skilltype);
        System.out.println(skilltype1+"=====");
        System.out.println(skilltype1.getStNumber());
        return skilltype1.getStNumber();
    }

    @Override
    public String modifyContent(PublishGameSkill publishGameSkill) {
        boolean title,describe;
        String skillId;
        List Personagetype = new ArrayList();
        title = checkSensitiveWords.checkWorks(publishGameSkill.getGsTitle());
        describe = checkSensitiveWords.checkWorks(publishGameSkill.getGsdescribe());
        if (title == true && describe == true) {
            Map skillInfo = new HashMap();
            System.out.println("游戏技能标题和描述验证成功");
            List gsPersonagetype = publishGameSkill.getGsPersonagetype();
            for (int i = 0; i < gsPersonagetype.size(); i++) {
                skillId = getSkillId(gsPersonagetype.get(i).toString());
                System.out.println(skillId);
                Personagetype.add(i,skillId);
                System.out.println(Personagetype);
            }
            System.out.println("个人风格集合截取前"+Personagetype.toString());//id
            skillId = Personagetype.toString().substring(1,Personagetype.toString().length()-1);
            System.out.println("个人风格集合截取后"+skillId);
            String imgString = publishGameSkill.getGsImg().toString();
            System.out.println("图片集合截取前"+imgString);
            imgString = imgString.substring(1,imgString.length()-1);
            System.out.println("图片集合截取后"+imgString);
            skillInfo.put("userId",publishGameSkill.getGsUserId());
            skillInfo.put("siTitle",publishGameSkill.getGsTitle());
            skillInfo.put("siType",publishGameSkill.getGsGameName());
            skillInfo.put("siMoney",publishGameSkill.getGsSerialnumber());
            skillInfo.put("siDescribe",publishGameSkill.getGsdescribe());
            skillInfo.put("siDuration",publishGameSkill.getGsDuration());
            skillInfo.put("siDate",publishGameSkill.getGsDate());
            skillInfo.put("siImg",imgString);
            skillInfo.put("siModifynumber",0);
            String pubSkillResult = pubSkill(skillInfo);
            System.out.println("技能信息表信息:"+skillInfo);
            System.out.println("插入技能信息表信息结果:"+pubSkillResult);
            GameSkillsInfo gameSkillsInfo = new GameSkillsInfo();
            //System.out.println(makePostId()+"=======");
            gameSkillsInfo.setGsISerialnumber(makeSkillID());
            gameSkillsInfo.setGsImage(publishGameSkill.getGsImage());
            gameSkillsInfo.setGsPersonagetype(skillId);
            //System.out.println(publishGameSkill.getGsGrade()+"1111");
            //System.out.println(getGradId(publishGameSkill.getGsGrade())+"2222");
            gameSkillsInfo.setGsGrade(getGradId(publishGameSkill.getGsGrade(),publishGameSkill.getGsGameName()));
            gameSkillsInfo.setGsType(Integer.parseInt(publishGameSkill.getGsType().toString()));
            gameSkillsInfo.setGsDistrict(getDistrict(publishGameSkill.getGsDistrict(),publishGameSkill.getGsGameName()));
            gameSkillsInfo.setGsWin(Integer.parseInt(publishGameSkill.getGsWin().toString()));
            gameSkillsInfo.setGsSex(Integer.parseInt(publishGameSkill.getGsSex().toString()));
            gameSkillsInfo.setGsSystem(Integer.parseInt(publishGameSkill.getGsSystem().toString()));
            gameSkillsInfo.setGsQuotingunit(Integer.parseInt(publishGameSkill.getGsQuotingunit().toString()));
            int insert = gameSkillsInfoMapper.insert(gameSkillsInfo);
            System.out.println("插入技能游戏表结果："+insert);
            if (pubSkillResult.equals("技能填写完成 进入审核状态") && insert == 1){
                return "游戏技能填写完成 进入审核状态";
            }else {
                return "modifyContent异常";
            }
        }
        return "游戏技能中有敏感词";
    }

    @Override
    public List getPostFamliy(String postStringId) {
        String str = getSkillId(postStringId);
        String stNumber;
        String stContent;
        String imageAddress;
        List list = new ArrayList();
        List<Skilltype> byFamilyAll = skilltypeService.getByFamilyAll(str);
        System.out.println("byFamilyAll:"+byFamilyAll);
        for (Skilltype skilltype : byFamilyAll) {
            PostPojo postPojo = new PostPojo();
            stNumber = skilltype.getStNumber();
            stContent = skilltype.getStContent();
            imageAddress = iImagesService.getImageAddress(stNumber);
            postPojo.setStNumber(stNumber);
            postPojo.setStContent(stContent);
            postPojo.setImageAddress(imageAddress);
            list.add(postPojo);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public String pubPost(Map postInfo) {
        boolean piTitle = checkSensitiveWords.checkWorks(postInfo.get("piTitle").toString());
        boolean piContent = checkSensitiveWords.checkWorks(postInfo.get("piContent").toString());
        System.out.println(postInfo.get("piType")+"=================");
        String piType = getSkillId(postInfo.get("piType").toString());
        System.out.println(piType+"*****************************");
        String piType1 = getSkillId(postInfo.get("piType").toString());
        System.out.println(piType1+"==*****************************");
        if (piTitle == true && piContent == true){
            System.out.println("帖子标题和内容检查成功");
            SimpleDateFormat current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式"yyyy-MM-dd HH:mm:ss"
            Date date = new Date();
            String format = current.format(date);
            try {
                date = current.parse(format);
                System.out.println(date+"111111111111111");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(current.format(new Date()));// new Date()为获取当前系统时间
            PostInfo newPostInfo = new PostInfo();
            newPostInfo.setPiId(0);
            newPostInfo.setPiNumber(makePostId());
            newPostInfo.setPiUserNumber(postInfo.get("piUsernumber").toString());
            newPostInfo.setPiTitle(postInfo.get("piTitle").toString());
            newPostInfo.setPiType(getSkillId(postInfo.get("piType").toString()));
            newPostInfo.setPiContent(postInfo.get("piContent").toString());
            newPostInfo.setPiImg(postInfo.get("piImg").toString());
            System.out.println("时间"+date);
            newPostInfo.setPiDate(date);
            newPostInfo.setPiReplies(0);
            newPostInfo.setPiState(2);
            int insert = postInfoMapper.insert(newPostInfo);
            System.out.println("插入帖子表结果"+insert);
            if (insert == 1){
                return "帖子填写完成 进入审核状态";
            }else {
                return "插入帖子失败";
            }
        }
        return "帖子内容不符合规范";
    }

    @Override
    public String makePostId() {
        String maxPostId = null;
        Boolean redisMaxPostId = redisTemplate.hasKey("maxPostId");
        System.out.println("redis中帖子id最大值："+redisMaxPostId);
        if (redisMaxPostId == false){
            maxPostId = postInfoMapper.getPostInfoMax();
            System.out.println("maxPostId为："+maxPostId);
        }
        int numberSkillid = (Integer.parseInt(maxPostId.substring(2)))+1;
        System.out.println(maxPostId.substring(2));
        maxPostId = "tz"+String.format("%05d",numberSkillid);
        System.out.println("加1后的帖子id："+maxPostId);
        return maxPostId;
    }

    @Override
    public List getSkill(int skill) {
        String skillId = "";
        switch (skill) {
            case 1:
                skillId="jn0001";
                break;
            case 2:
                skillId="jn0008";
                break;
            case 3:
                skillId="jn0016";
                break;
            case 4:
                skillId="jn0030";
                break;
            case 5:
                skillId="jn0039";
                break;
            default:
                break;
        }
        List<Skilltype> byFamilyAll = skilltypeService.getByFamilyAll(skillId);
        return byFamilyAll;
    }

    @Override
    public List getprofessionalSkill(int skill) {
        String skillId = "";
        switch (skill) {
            case 1:
                skillId="jn0040";
                break;
            case 2:
                skillId="jn0049";
                break;
            case 3:
                skillId="jn0066";
                break;
            case 4:
                skillId="jn0073";
                break;
            case 5:
                skillId="jn0082";
                break;
            case 6:
                skillId="jn0082";
                break;
            default:
                break;
        }
        List<Skilltype> byFamilyAll = skilltypeService.getByFamilyAll(skillId);
        return byFamilyAll;
    }

}
