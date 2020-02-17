package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.VirtualMapper;
import cn.qiandao.shengqianyoudao.pojo.Cashrecords;
import cn.qiandao.shengqianyoudao.pojo.Userinfo;
import cn.qiandao.shengqianyoudao.pojo.Virtualcurrencyrecords;
import cn.qiandao.shengqianyoudao.service.CashrecordsService;
import cn.qiandao.shengqianyoudao.service.UserService;
import cn.qiandao.shengqianyoudao.service.VirtualService;
import cn.qiandao.shengqianyoudao.util.IDUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lxy
 * @date 2020/1/4 0004 18:46
 **/
@Service
public class VirtualServiceImpl implements VirtualService {
    private Logger log = LoggerFactory.getLogger(VirtualServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private VirtualMapper virtualMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CashrecordsService cashrecordsService;
    @Override
    public List<Virtualcurrencyrecords> getListVirtual(String vuid) {
        Virtualcurrencyrecords vcc = new Virtualcurrencyrecords();
        vcc.setVcrUserserialnumber(vuid);
        return virtualMapper.select(vcc);
    }

    @Override
    public int addVirtualcords(Virtualcurrencyrecords v) {
        v.setVcrTime(new Date());
        String virtual = (String) redisTemplate.opsForValue().get("金币");
        log.info("旧值是" + virtual);
        String jb = IDUtil.getNewEquipmentNo("jb", virtual);
        log.info("新值是" + virtual);
        redisTemplate.opsForValue().set("金币",jb);
        v.setVcrNumber(jb);
        return virtualMapper.insert(v);
    }

    @Override
    public int delVirtualcords(String vid) {
        Virtualcurrencyrecords vcc = new Virtualcurrencyrecords();
        vcc.setVcrNumber(vid);
        return virtualMapper.delete(vcc);
    }

    @Override
    public int updateVirtualcords(Virtualcurrencyrecords v) {
        return virtualMapper.updateByPrimaryKeySelective(v);
    }

    @Override
    @Transactional
    public String sellByRate(double rate,double vc,String uid){
        try {
            double total = rate * vc;
            Userinfo o = (Userinfo) redisTemplate.opsForValue().get("用户" + uid);
            log.info("redis用户:" + o);
            if(o == null){
                o = userService.findById(uid);
                log.info("用户：" + o);
                redisTemplate.opsForValue().set("用户" + uid,o);
            }
            double vcrGatheringbalance = o.getVcbalance().doubleValue();
            double vcrVcbalance = o.getCashbalance().doubleValue();
            log.info(String.valueOf(vcrGatheringbalance));
            if (vcrGatheringbalance < total) {
                return "金额超出范围";
            }
            Virtualcurrencyrecords vcc = new Virtualcurrencyrecords();
            vcc.setVcrChange(BigDecimal.valueOf(vc));
            vcc.setVcrChangetype(0);
            vcc.setVcrTime(new Date());
            vcc.setVcrCause(5);
            vcc.setVcrUserserialnumber(uid);
            vcc.setVcrGatheringbalance(BigDecimal.valueOf(vcrGatheringbalance - vc));
            int i1 = addVirtualcords(vcc);
            Cashrecords cashrecords = new Cashrecords();
            cashrecords.setCrChange(BigDecimal.valueOf(total));
            cashrecords.setCrChangetype(1);
            cashrecords.setCrDate(new Date());
            cashrecords.setCrCause("1");
            cashrecords.setCrGatheringusernumber(uid);
            cashrecords.setCrGatheringbalance(BigDecimal.valueOf(vcrVcbalance-total));
            int i2 = cashrecordsService.addCashrecords(cashrecords);
            JSONObject jo = new JSONObject();
            jo.put("cash", o.getCashbalance().doubleValue()-total);
            jo.put("gold", o.getVcbalance().doubleValue()-vc);
            return String.valueOf(jo);
        }catch (Exception e){
            return "出售失败";
        }
    }
}
