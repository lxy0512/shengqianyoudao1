package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Table(name = "taskorder")
public class Taskorder implements Serializable {


    /**
     * 任务编号
     */
    @Id
    private String toTasknumber;

    /**
     * 发布任务的用户编号
     */
    private String toReleaseusernumber;

    /**
     * 接取任务的用户编号
     */
    private String toAccpetusernumber;

    /**
     * 接受时间
     */
    private Date toAccpetdate;

    /**
     * 最后更改时间
     */
    private Date toUpdate;

    /**
     * 数量
     */
    private Integer toQuantity;

    /**
     * 金额
     */
    private Long toMoney;

    /**
     * 订单编号
     */
    private String toNumber;

    /**
     * 订单状态（填写序号）
     */
    private Integer toState;

    private static final long serialVersionUID = 1L;

}