package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @Description
 * @Author wt
 * @data
 */
@Entity
@Data
public class User implements Serializable {

    //用户编号
    private String number;

    //用户名
    private String username;

    //密码
    private String pwd;

    //头像图片
    private String img;

    //背景图片
    private String bgimg;

    //手机号码
    private String phone;

    //等级，数据库为int
    private Integer grade;

    //在线状态，数据库为int
    private Integer state;

    //邀请码
    private String invitationcode;

    //个性签名
    private String signature;

    //注册时间，数据库为date
    private Date registrationdate;

    //现金余额，数据库为decimal
    private BigDecimal cashbalance;

    //虚拟币余额，数据库为decimal
    private BigDecimal vcbalance;

    //经验值
    private int exp;

    //角色
    private String role;

    //权限
    private ArrayList<String> permissions;

    //名字
    private String name;

    //姓氏
    private String familyname;

    //语言，数据库为int
    private Integer language;

    //性别，数据库为int
    private Integer gender;

    //出生日期，数据库为date
    private Date birth;

    //地区，数据库为int，连表
    private Integer area;

    //证件类型，数据库为int
    private Integer documenttype;

    //证件号码，数据库为bigint
    private Long documentnumber;

    //学历，数据库为int
    private Integer educationbackground;

    //教育经历
    private String educationexperience;

    //行业，数据库为int，连表
    private Integer industry;

    //从业经历
    private String workingexperience;

    //收入范围，数据库为int
    private Integer incomerange;

    //电子邮件
    private String email;

    //微信
    private String wechat;

    //信用积分
    private int creditscore;

    @Override
    public String toString() {
        return "{" +
                "number='" + number + '\'' +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", img='" + img + '\'' +
                ", bgimg='" + bgimg + '\'' +
                ", phone='" + phone + '\'' +
                ", grade=" + grade +
                ", state=" + state +
                ", invitationcode='" + invitationcode + '\'' +
                ", signature='" + signature + '\'' +
                ", registrationdate=" + registrationdate +
                ", cashbalance=" + cashbalance +
                ", vcbalance=" + vcbalance +
                ", exp=" + exp +
                ", role='" + role + '\'' +
                ", permissions=" + permissions +
                ", name='" + name + '\'' +
                ", familyname='" + familyname + '\'' +
                ", language=" + language +
                ", gender=" + gender +
                ", birth=" + birth +
                ", area=" + area +
                ", documenttype=" + documenttype +
                ", documentnumber=" + documentnumber +
                ", educationbackground=" + educationbackground +
                ", educationexperience='" + educationexperience + '\'' +
                ", industry=" + industry +
                ", workingexperience='" + workingexperience + '\'' +
                ", incomerange=" + incomerange +
                ", email='" + email + '\'' +
                ", wechat='" + wechat + '\'' +
                '}';
    }
}
