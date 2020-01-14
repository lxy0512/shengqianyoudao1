package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Table(name = "skillorder")
public class Skillorder implements Serializable {
    /**
    * 订单编号
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soNumber;

    /**
    * 技能编号
    */
    @Column(name = "so_skillnumber")
    private String soSkillnumber;

    /**
    * 购买技能的用户编号
    */
    private String soBuyusernumber;

    /**
    * 购买时间
    */
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date soPurchasingdate;

    /**
     * 最后更改时间
     */
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date soEnddate;

    /**
    * 数量
    */
    private Integer soQuantity;

    /**
    * 金额
    */
    private BigDecimal soMoney;

    /**
    * 付款方式（填写序号）
    */
    private Integer soPaymentmethod;

    /**
    * 订单状态（填写序号）
    */
    private Integer soState;

    /**
     * 发布技能的用户
     */
    private String soSkilluser;

    private static final long serialVersionUID = 1L;

    @Transient
    private String title;



}