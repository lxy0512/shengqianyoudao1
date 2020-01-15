package cn.qiandao.shengqianyoudao.pojo;

import javax.persistence.Entity;
import java.util.List;

/**
 * 发布游戏技能pojo
 * zrf
 */
@Entity
public class PublishGameSkill {
    /**
     * 用户id
     */
    private String gsUserId;
    /**
     * 游戏名称
     */
    private String gsGameName;
    /**
     * 段位图片
     */
    private String gsImage;
    /**
     * 标题
     */
    private String gsTitle;
    /**
     * 个人风格
     */
    private List gsPersonagetype;
    /**
     * 游戏段位/游戏等级
     */
    private String gsGrade;
    /**
     * 技能类型
     */
    private Integer gsType;
    /**
     * 游戏区服
     */
    private String gsDistrict;
    /**
     * 是否上分
     */
    private Integer gsWin;
    /**
     * 男神/女神
     */
    private Integer gsSex;
    /**
     * 说明
     */
    private String gsdescribe;
    /**
     * 描述图片
     */
    private List gsImg;
    /**
     * 交付时间
     */
    private Integer gsDuration;
    /**
     * 报价单位
     */
    private Integer gsQuotingunit;
    /**
     * 报价
     */
    private Integer gsSerialnumber;
    /**
     * 接单时间
     */
    private String gsDate;
    /**
     * 操作系统
     */
    private Integer gsSystem;

    public Integer getGsSystem() {
        return gsSystem;
    }

    public void setGsSystem(Integer gsSystem) {
        this.gsSystem = gsSystem;
    }

    public String getGsUserId() {
        return gsUserId;
    }

    public void setGsUserId(String gsUserId) {
        this.gsUserId = gsUserId;
    }

    public String getGsGameName() {
        return gsGameName;
    }

    public void setGsGameName(String gsGameName) {
        this.gsGameName = gsGameName;
    }

    public String getGsImage() {
        return gsImage;
    }

    public void setGsImage(String gsImage) {
        this.gsImage = gsImage;
    }

    public String getGsTitle() {
        return gsTitle;
    }

    public void setGsTitle(String gsTitle) {
        this.gsTitle = gsTitle;
    }

    public List getGsPersonagetype() {
        return gsPersonagetype;
    }

    public void setGsPersonagetype(List gsPersonagetype) {
        this.gsPersonagetype = gsPersonagetype;
    }

    public String getGsGrade() {
        return gsGrade;
    }

    public void setGsGrade(String gsGrade) {
        this.gsGrade = gsGrade;
    }

    public Integer getGsType() {
        return gsType;
    }

    public void setGsType(Integer gsType) {
        this.gsType = gsType;
    }

    public String getGsDistrict() {
        return gsDistrict;
    }

    public void setGsDistrict(String gsDistrict) {
        this.gsDistrict = gsDistrict;
    }

    public Integer getGsWin() {
        return gsWin;
    }

    public void setGsWin(Integer gsWin) {
        this.gsWin = gsWin;
    }

    public Integer getGsSex() {
        return gsSex;
    }

    public void setGsSex(Integer gsSex) {
        this.gsSex = gsSex;
    }

    public String getGsdescribe() {
        return gsdescribe;
    }

    public void setGsdescribe(String gsdescribe) {
        this.gsdescribe = gsdescribe;
    }

    public List getGsImg() {
        return gsImg;
    }

    public void setGsImg(List gsImg) {
        this.gsImg = gsImg;
    }

    public Integer getGsDuration() {
        return gsDuration;
    }

    public void setGsDuration(Integer gsDuration) {
        this.gsDuration = gsDuration;
    }

    public Integer getGsQuotingunit() {
        return gsQuotingunit;
    }

    public void setGsQuotingunit(Integer gsQuotingunit) {
        this.gsQuotingunit = gsQuotingunit;
    }

    public Integer getGsSerialnumber() {
        return gsSerialnumber;
    }

    public void setGsSerialnumber(Integer gsSerialnumber) {
        this.gsSerialnumber = gsSerialnumber;
    }

    public String getGsDate() {
        return gsDate;
    }

    public void setGsDate(String gsDate) {
        this.gsDate = gsDate;
    }
}
