package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.CashrecordsMapper;
import cn.qiandao.shengqianyoudao.pojo.Cashrecords;
import cn.qiandao.shengqianyoudao.service.CashrecordsService;
import cn.qiandao.shengqianyoudao.util.IDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lxy
 * @date 2020/1/4 0004 17:20
 **/
@Service
public class CashrecordsServiceImpl implements CashrecordsService {
    private Logger log = LoggerFactory.getLogger(CashrecordsServiceImpl.class);
    @Autowired
    private CashrecordsMapper cashrecordsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<Cashrecords> getCashreByCuId(String cuid) {
        Cashrecords cashrecords = new Cashrecords();
        cashrecords.setCrGatheringusernumber(cuid);
        return cashrecordsMapper.select(cashrecords);

    }

    @Override
    public int addCashrecords(Cashrecords cashrecords) {
        redisTemplate.opsForList().rightPush("现金",cashrecords);
        cashrecords.setCrDate(new Date());
        String virtual = (String) redisTemplate.opsForValue().get("金币");
        log.info("旧值是" + virtual);
        String jl = IDUtil.getNewEquipmentNo("jl", virtual);
        log.info("新值是" + virtual);
        redisTemplate.opsForValue().set("金币",jl);
        cashrecords.setCrNumber(jl);
        return cashrecordsMapper.insert(cashrecords);
    }

    @Override
    public int delCashrecords(String vid) {
        Cashrecords cashrecords = new Cashrecords();
        cashrecords.setCrNumber(vid);
        return cashrecordsMapper.delete(cashrecords);
    }

    @Override
    public int updateCashrecords(Cashrecords cashrecords) {
        return cashrecordsMapper.updateByPrimaryKeySelective(cashrecords);
    }
}
