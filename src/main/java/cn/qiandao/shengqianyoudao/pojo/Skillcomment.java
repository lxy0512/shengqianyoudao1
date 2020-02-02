package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "skillcomment")
public class Skillcomment implements Serializable {
    /**
    * id
    */
    private Integer scId;

    /**
    * 评论编号
    */
    private String scNumber;

    /**
    * 技能编号
    */
    private String scSkillnumber;

    /**
    * 内容
    */
    private String scContent;

    /**
    * 技能评分
    */
    private Integer scGrade;

    /**
    * 发布时间
    */
    private Date scDate;

    /**
    * 发布人编号
    */
    private String scUsernumber;
    @Transient
    private User user;

    private static final long serialVersionUID = 1L;
}