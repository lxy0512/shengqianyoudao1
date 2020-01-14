package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "cashrecords")
@ToString
public class Cashrecords implements Serializable {
    /**
     * 记录编号
     */
    @Id
    private String crNumber;
    /**
     * 收到资金的用户编号
     */
    @Column(name = "cr_gatheringusernumber")
    private String crGatheringusernumber;

    /**
     * 变化量(0为减，1为正)
     */
    @Column(name = "cr_change")
    private BigDecimal crChange;
    /**
     * 变化类型（填写序号）
     */
    @Column(name = "cr_changetype")
    private Integer crChangetype;

    /**
     * 时间
     */
    @Column(name = "cr_date")
    private Date crDate;

    /**
     * 付款方式（1:微信、2：支付宝、3：零钱）
     */
    @Column(name = "cr_paymentmethod")
    private Integer crPaymentmethod;
    /**
     * 收到资金的用户金钱
     */
    @Column(name = "cr_gatheringbalance")
    private BigDecimal crGatheringbalance;
    /**
     * 原因（填写序号）
     */
    @Column(name = "cr_cause")
    private String crCause;
    /**
     * 发出资金的用户编号
     */
    @Column(name = "cr_payusernumber")
    private String crPayusernumber;
    /**
     * 发出资金的用户编号
     */
    @Column(name = "cr_paybalance")
    private BigDecimal crPaybalance;

    private static final long serialVersionUID = 1L;

}