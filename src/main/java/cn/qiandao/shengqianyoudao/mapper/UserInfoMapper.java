package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Mapper
public interface UserInfoMapper {

    int changeUserInfo(@Param("user") User user);
    int changeUserBasicInfo(@Param("user") User user);
    ArrayList<Breviary> attention1(@Param("usernumber1")String usernumber1);
    ArrayList<Breviary> attention2(@Param("usernumber2")String usernumber2);
    int aaa(@Param("usernumber")String usernumber,@Param("fields1")String fields1,@Param("fields2")String fields2,@Param("code")int code);
    String getICode(@Param("icode")String icode);
    Breviary getIUser(@Param("usernumber")String usernumber);
    int saveICode(@Param("usernumber")String usernumber,@Param("code")String code,@Param("date")Date date);
    int repeatICode(@Param("usernumber")String usernumber,@Param("code")String code);
    int deleteUser(String usernumber);
    List<Breviary> getAllUserInfo(int page);
    User getUserInfo(@Param("usernumber")String usernumber);
}
