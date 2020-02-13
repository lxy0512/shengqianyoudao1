package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Collectionrecords;
import cn.qiandao.shengqianyoudao.pojo.Skillsinfo;

import java.util.List;

public interface CollectionrecordsService {
    Collectionrecords selectOne(Collectionrecords collectionrecords);
    int insert(Collectionrecords collectionrecords);
    List<Skillsinfo> selectAll(String userid);
}
