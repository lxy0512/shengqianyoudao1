package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author fyy
 * @date 2020/1/8 15:57
 **/
@Table(name = "userinfo")
@Data
public class Userinfo {
    @Id
    private Integer uiId;
    //用户编号
    @Column(name = "ui_number")
    private String number;

    //用户名
    @Column(name = "ui_name")
    private String username;

    //密码
    @Column(name = "ui_pwd")
    private String pwd;

    //头像图片
    @Column(name = "ui_img")
    private String img;

    //背景图片
    @Column(name = "ui_bgimg")
    private String bgimg;

    //手机号码
    @Column(name = "ui_phone")
    private String phone;

    //等级，数据库为int
    @Column(name = "ui_grade")
    private Integer grade;

    //在线状态，数据库为int
    @Column(name = "ui_state")
    private Integer state;

    //邀请码
    @Column(name = "ui_code")
    private String invitationcode;

    //个性签名
    @Column(name = "ui_signature")
    private String signature;

    //注册时间，数据库为date
    @Column(name = "ui_registrationdate")
    private Date registrationdate;

    //现金余额，数据库为decimal
    @Column(name = "ui_cashbalance")
    private BigDecimal cashbalance;

    //虚拟币余额，数据库为decimal
    @Column(name = "ui_virtualcurrencybalance")
    private BigDecimal vcbalance;

    //经验值
    @Column(name = "ui_exp")
    private int exp;

}
