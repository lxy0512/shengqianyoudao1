package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "skilluserrelationship")
public class Skilluserrelationship implements Serializable {
    /**
    * id
    */
    @Id
    private Integer surId;

    /**
    * 技能编号
    */
    private String surSkillnumber;

    /**
    * 用户编号
    */
    private String surUsernumber;

    /**
    * 发布时间
    */
    private Date surDate;

    private static final long serialVersionUID = 1L;
}