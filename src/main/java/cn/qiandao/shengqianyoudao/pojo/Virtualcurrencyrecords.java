package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 金币
 */
@Data
@Table(name = "virtualcurrencyrecords")
public class Virtualcurrencyrecords implements Serializable {
    /**
     * id
     */
    @Id
    private Integer vcrId;

    /**
     * 记录编号
     */
    private String vcrNumber;

    /**
     * 变化量
     */
    @Column(name = "vcr_change")
    private BigDecimal vcrChange;

    /**
     * 变化类型（填写序号）
     */
    private Integer vcrChangetype;

    /**
     * 时间
     */
    private Date vcrTime;

    /**
     * 收到金币的用户编号
     */
    private String vcrUserserialnumber;
    /**
     * 收到金币的用户金币
     */
    @Column(name = "vcr_gatheringbalance")
    private BigDecimal vcrGatheringbalance;
    /**
     * 原因（填写序号）
     */
    private Integer vcrCause;

    private static final long serialVersionUID = 1L;
}