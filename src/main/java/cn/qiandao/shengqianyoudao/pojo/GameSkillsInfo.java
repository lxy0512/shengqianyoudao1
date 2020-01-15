package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 游戏技能表
 * zrf
 */
@Data
@Table(name = "gameskillsinfo")
public class GameSkillsInfo {

    @Id
    @Column(name = "gsi_serialnumber")
    private String gsISerialnumber;
    @Column(name = "gsi_image")
    private String gsImage;
    @Column(name = "gsi_personagetype")
    private String gsPersonagetype;
    @Column(name = "gsi_grade")
    private String gsGrade;
    @Column(name = "gsi_type")
    private Integer gsType;
    @Column(name = "gsi_district")
    private String gsDistrict;
    @Column(name = "gsi_win")
    private Integer gsWin;
    @Column(name = "gsi_sex")
    private Integer gsSex;
    @Column(name = "gsi_system")
    private Integer gsSystem;
    @Column(name = "gsi_quotingunit")
    private Integer gsQuotingunit;
}
