package cn.qiandao.shengqianyoudao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Set;

/**
 * 序列化与反序列化工具类
 *
 * @author lxy
 * @date 2020/2/3 0003 16:40
 **/
public class FastjsonUtil {
    /**
     * 序列化
     *
     * @param object 序列化对象
     * @return
     */
    public static String serialize(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * Set反序列化
     *
     * @param jsonStr 序列化结果
     * @param clazz 泛型class类
     * @return
     */
    public static <T> Set<T> deserializeToSet(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, new TypeReference<Set<T>>(clazz) {
        });
    }
}

