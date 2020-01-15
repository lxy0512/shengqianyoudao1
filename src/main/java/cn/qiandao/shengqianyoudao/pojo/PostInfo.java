package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 帖子信息表 postinfo
 * zrf
 */
@Data
@Table(name = "postinfo")
public class PostInfo {
    /**
     * id
     */
    @Column(name = "pi_id")
    private Integer piId;
    /**
     * 帖子编号
     */
    @Column(name = "pi_number")
    private String piNumber;
    /**
     * 用户编号
     */
    @Column(name = "pi_usernumber")
    private String piUserNumber;
    /**
     * 帖子标题
     */
    @Column(name = "pi_title")
    private String piTitle;
    /**
     * 帖子类型
     */
    @Column(name = "pi_type")
    private String piType;
    /**
     * 帖子内容
     */
    @Column(name = "pi_content")
    private String piContent;
    /**
     * 图片
     */
    @Column(name = "pi_img")
    private String piImg;
    /**
     * 发布时间
     */
    @Column(name = "pi_date")
    private Date piDate;
    /**
     * 回复量
     */
    @Column(name = "pi_replies")
    private Integer piReplies;
    /**
     * 帖子是否可显示状态
     */
    @Column(name = "pi_state")
    private Integer piState;
}
