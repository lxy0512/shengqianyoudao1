package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;

public interface CollectionrecordsService {
    Collectionrecords selectOne(Collectionrecords collectionrecords);
    int insert(Collectionrecords collectionrecords);
}
