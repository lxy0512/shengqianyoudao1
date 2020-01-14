package cn.qiandao.shengqianyoudao.service.impl;


import cn.qiandao.shengqianyoudao.mapper.SkillorderMapper;
import cn.qiandao.shengqianyoudao.pojo.Skillorder;
import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;
import cn.qiandao.shengqianyoudao.service.SkillorderService;
import cn.qiandao.shengqianyoudao.service.SkillsinfoService;
import cn.qiandao.shengqianyoudao.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fyy
 * @date 2020/1/8 10:02
 **/
@Slf4j
@Service
public class SkillorderServiceImpl implements SkillorderService {

    @Autowired
    private SkillorderMapper skillorderMapper;
    @Autowired
    private SkillsinfoService skillsinfoService;
    //雪花算法
    private SnowFlake snowFlake = new SnowFlake(2,3);

    @Override
    public Long addKillOrder(Skillorder skillorder) {
        //当前时间（为了简化模板）
        Date orderTime = new Date();
        //雪花算法生成随机数
        Long orderNo = snowFlake.nextId();
        if (skillorder.getSoSkillnumber() != null&&
                skillorder.getSoSkilluser() != null&&
                skillorder.getSoQuantity() != null&&
                skillorder.getSoMoney() != null&&
                skillorder.getSoPaymentmethod() != null&&
                skillorder.getSoState() != null){
            //订单编号
            skillorder.setSoNumber(orderNo);
            //购买的时间(订单时间)
            skillorder.setSoPurchasingdate(orderTime);
            Skillsinfo skillsinfo = skillsinfoService.selectBySiSerialnumber(skillorder.getSoSkillnumber());
            skillorder.setTitle(skillsinfo.getSiTitle());
            int i = skillorderMapper.insert(skillorder);
            System.out.println(i);
            return orderNo;
        }else {
            return null;
        }

    }
    //修改技能订单状态
    @Override
    public int updateOrder(Long orderId,int state) {
        Skillorder skillorder = new Skillorder();
        skillorder.setSoNumber(orderId);
        skillorder.setSoState(state);
        return skillorderMapper.updateByPrimaryKeySelective(skillorder);
    }
    //查询订单信息
    @Override
    public Skillorder selectOrder(Long orderid) {
//        Skillorder skillorder = new Skillorder();
//        skillorder.setSoNumber(orderid);
        return skillorderMapper.selectByPrimaryKey(orderid);
    }

    @Override
    public List<Skillorder> selectUserOrder(String user) {
        Skillorder skillorder = new Skillorder();
        skillorder.setSoSkilluser(user);
        List<Skillorder> orders = skillorderMapper.select(skillorder);
        return orders;
    }

    @Override
    public List<Skillorder> selectByOrder(String Byuser) {
        Skillorder skillorder = new Skillorder();
        skillorder.setSoBuyusernumber(Byuser);
        List<Skillorder> orders = skillorderMapper.select(skillorder);
        return orders;
    }

    /**
     * 根据技能编号查订单数
     *
     * @param skillId
     * @return
     */
    @Override
    public int selectBySkillIdCount(String skillId) {
        Skillorder skillorder = new Skillorder();
        skillorder.setSoSkillnumber(skillId);
        return skillorderMapper.selectCount(skillorder);
    }

}
