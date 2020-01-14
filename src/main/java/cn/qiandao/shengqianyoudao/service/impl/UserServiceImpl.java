package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.UserMapper;
import cn.qiandao.shengqianyoudao.pojo.Userinfo;
import cn.qiandao.shengqianyoudao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fyy
 * @date 2019/12/25 16:03
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Userinfo user) {
        return userMapper.insert(user);
    }

    @Override
    public Userinfo findById(String id) {
        Userinfo u = new Userinfo();
        u.setNumber(id);
        return userMapper.selectOne(u);
        //return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Userinfo user) {
        return userMapper.delete(user);
    }

    @Override
    public List<Userinfo> findAll() {
        return userMapper.selectAll();
    }

}
