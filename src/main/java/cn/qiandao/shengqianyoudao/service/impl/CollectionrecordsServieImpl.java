package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.CollectionrecordsMapper;
import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;
import cn.qiandao.shengqianyoudao.service.CollectionrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lxy
 * @date 2020/2/5 0005 16:52
 **/
@Service
public class CollectionrecordsServieImpl implements CollectionrecordsService {
    @Autowired
    private CollectionrecordsMapper collectionrecordsMapper;
    @Override
    public Collectionrecords selectOne(Collectionrecords collectionrecords) {
        return collectionrecordsMapper.selectOne(collectionrecords);
    }

    @Override
    public int insert(Collectionrecords collectionrecords) {
        return collectionrecordsMapper.insert(collectionrecords);
    }
}
