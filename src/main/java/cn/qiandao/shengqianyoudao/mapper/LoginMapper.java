package cn.qiandao.shengqianyoudao.mapper;

import cn.qiandao.shengqianyoudao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.ArrayList;

/**
 * @Description
 * @Author wt
 * @data
 */
@Mapper
public interface LoginMapper {
    String getPasswordByUsername(@Param("name") String name);
    String getPhone(@Param("phone") String phone);
    int saveRegisterUser(@Param("user") User user);
    int saveRegisterUserbasic(@Param("user") User user);
    User getUserInfo(@Param("getPhone") String getPhone);
    String getNumber();
    int saveUserRole(@Param("userNumber") String userNumber);
    String getUserByNumberOnRole(@Param("userNumber") String userNumber);
    String getrole(@Param("number") String number);
    ArrayList<String> getpermissions(@Param("role") String role);
    int changPwd(@Param("phone") String phone, @Param("pwd") String pwd);
    int saveInvitationInfo(@Param("fNumber") String fNumber, @Param("number") String number, @Param("date") Date date);
    int saveNovTaskInfo(@Param("number") String number, @Param("datetime") String datetime, @Param("fname") String fname);
    String getNumberByIcode(@Param("icode") String icode);
    int getCreditScore(@Param("userNumber") String userNumber);
}
