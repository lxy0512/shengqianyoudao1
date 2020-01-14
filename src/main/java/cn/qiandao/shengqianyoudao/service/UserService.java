package cn.qiandao.shengqianyoudao.service;

import cn.qiandao.shengqianyoudao.pojo.Userinfo;

import java.util.List;

public interface UserService {
    public int save(Userinfo user);
    public Userinfo findById(String id);
    public int delete(Userinfo user);
    public List<Userinfo> findAll();
}
