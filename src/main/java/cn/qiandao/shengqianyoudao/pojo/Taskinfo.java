package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Table(name = "taskinfo")
public class Taskinfo implements Serializable {
    /**
     * 任务编号
     */
    @Id
    private String tiTasknumber;

    /**
     * 用户编号
     */
    @Column(name = "ti_usernumber")
    private String tiUsernumber;

    /**
     * 任务标题
     */
    @Column(name = "ti_title")
    private String tiTitle;

    /**
     * 任务类型（填写序号）
     */
    @Column(name = "ti_type")
    private Integer tiType;

    /**
     * 任务金额
     */
    @Column(name = "ti_money")
    private BigDecimal tiMoney;

    /**
     * 截止人数
     */
    @Column(name = "ti_peoplelimit")
    private Integer tiPeoplelimit;

    /**
     * 截止时间
     */
    @Column(name = "ti_datelimit")
    private Date tiDatelimit;

    /**
     * 提交任务的时长
     */
    @Column(name = "ti_timelimit")
    private Integer tiTimelimit;

    /**
     * 任务可完成次数
     */
    @Column(name = "ti_timeslimit")
    private Integer tiTimeslimit;

    /**
     * 任务相关链接
     */
    @Column(name = "ti_link")
    private String tiLink;

    /**
     * 任务描述
     */
    @Column(name = "ti_describe")
    private String tiDescribe;

    /**
     * 任务要求
     */
    @Column(name = "ti_require")
    private String tiRequire;

    /**
     * 完成人数
     */
    @Column(name = "ti_completepeople")
    private Integer tiCompletepeople;

    /**
     * 任务图片
     */
    @Column(name = "ti_msimg")
    private String tiMsimg;

    /**
     * 浏览量
     */
    @Column(name = "ti_pageview")
    private Integer tiPageview;

    /**
     * 任务是否可显示状态
     */
    @Column(name = "ti_state")
    private Integer tiState;

    /**
     * 发布时间
     */
    @Column(name = "ti_date")
    private Date tiDate;

    /**
     * 任务经验
     */
    @Column(name = "ti_experience")
    private Integer tiExperience;

    /**
     * 奖励24小时内审核
     */
    @Column(name = "ti_audit")
    private Integer tiAudit;

    /**
     * 任务要求图片
     */
    @Column(name = "ti_yqimg")
    private String tiYqimg;
    /**
     * 用户
     */

    @Transient
    private Userinfo user;

    private static final long serialVersionUID = 1L;

}