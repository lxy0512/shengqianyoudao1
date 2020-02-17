package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "skillsinfo")
public class Skillsinfo implements Serializable {

    /**
     * 技能编号
     */
    @Id
    private String siSerialnumber;

    /**
     * 技能标题
     */
    @Column(name = "si_title")
    private String siTitle;

    /**
     * 技能类型（填写序号）
     */
    @Column(name = "si_type")
    private String siType;

    /**
     * 技能金额
     */
    @Column(name = "si_money")
    private BigDecimal siMoney;

    /**
     * 技能描述
     */
    @Column(name = "si_describe")
    private String siDescribe;

    /**
     * 技能时间
     */
    @Column(name = "si_duration")
    private Integer siDuration;

    /**
     * 接单时间
     */
    @Column(name = "si_date")
    private String siDate;

    /**
     * 上传图片
     */
    @Column(name = "si_img")
    private String siImg;
    @Transient
    private String[] siImgages;

    /**
     * 官方认证（填写序号）
     */
    @Column(name = "si_authority")
    private Integer siAuthority;

    /**
     * 可修改次数
     */
    @Column(name = "si_modifynumber")
    private Integer siModifynumber;

    /**
     * 技能是否可显示状态
     */
    @Column(name = "si_state")
    private Integer siState;

    /**
     * 进行状态
     */
    @Column(name = "si_tostate")
    private Integer siTostate;

    /**
     * 技能评分
     */
    @Column(name = "si_grade")
    private Integer siGrade;
    @Transient
    private User u;
    @Transient
    /**
     *成单量
     */
    private Integer singularization;
    @Transient
    /**
     *评价星级
     */
    private double morningstarRating;
    private static final long serialVersionUID = 1L;
}