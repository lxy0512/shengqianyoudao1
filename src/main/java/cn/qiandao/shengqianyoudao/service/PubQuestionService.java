package cn.qiandao.shengqianyoudao.service;

import java.util.Map;

/**
 * zrf
 */
public interface PubQuestionService {
    //个人问卷
    int insertPerson(Map persion);
    //联盟问卷
    int insertAlliance(Map alliance);
}
