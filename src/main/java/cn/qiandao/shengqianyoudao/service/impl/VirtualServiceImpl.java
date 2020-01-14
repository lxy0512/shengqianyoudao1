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
            Userinfo byId = userService.findById(uid);
            if (byId == null) {
                return "用户不存在";
            }
            double vcrGatheringbalance = byId.getVcbalance().doubleValue();
            if (vcrGatheringbalance < total) {
                return "金额超出范围";
            }
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Future<Integer> future1 = executor.submit(() -> {
                System.out.println("task is executed");
                Virtualcurrencyrecords vcc = new Virtualcurrencyrecords();
                vcc.setVcrChange(BigDecimal.valueOf(vc));
                vcc.setVcrChangetype(0);
                vcc.setVcrTime(new Date());
                vcc.setVcrCause(5);
                vcc.setVcrUserserialnumber(uid);
                vcc.setVcrGatheringbalance(BigDecimal.valueOf(vcrGatheringbalance - vc));
                int i = addVirtualcords(vcc);
                return i;
            });
            if (future1.get() == 1) {
                log.info("金币插入成功");
            } else {
                log.info("金币插入失败");
            }
            Future<Integer> future2 = executor.submit(() -> {
                System.out.println("task is executed");
                Cashrecords cashrecords = new Cashrecords();
                cashrecords.setCrChange(BigDecimal.valueOf(total));
                cashrecords.setCrChangetype(1);
                cashrecords.setCrDate(new Date());
                cashrecords.setCrCause("1");
                cashrecords.setCrGatheringusernumber(uid);
                cashrecords.setCrGatheringbalance(BigDecimal.valueOf(byId.getCashbalance().doubleValue() - total));
                int i = cashrecordsService.addCashrecords(cashrecords);
                return i;
            });
            if (future2.get() == 1) {
                log.info("现金插入成功");
            } else {
                log.info("现金插入失败");
            }

            double cash = (double) redisTemplate.opsForValue().get("cash");
            cash -= total;
            double gold = (double) redisTemplate.opsForValue().get("gold");
            gold -= vc;
            JSONObject jo = new JSONObject();
            jo.put("cash", cash);
            jo.put("gold", gold);
            return String.valueOf(jo);
        }catch (Exception e){
            return "出售失败";
        }
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future = executor.submit(() -> {
            System.out.println("task is executed");
            return 1;
        });
        System.out.println("task execute time is: " + future.get());
    }
}
