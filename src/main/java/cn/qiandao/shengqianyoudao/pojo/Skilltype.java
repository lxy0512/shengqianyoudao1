package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author lxy
 * @date 2020/1/3 0003 23:38
 **/
@Data
@Table(name = "skilltype")
public class Skilltype implements Serializable {
    @Id
    private Integer stId;
    @Column(name = "st_number")
    private String stNumber;
    @Column(name = "st_familynumber")
    private String stFamilynumber;
    @Column(name = "st_content")
    private String stContent;
}
