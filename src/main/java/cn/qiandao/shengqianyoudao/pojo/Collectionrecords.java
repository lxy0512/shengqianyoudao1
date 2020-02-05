package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lxy
 * @date 2020/2/3 0003 16:22
 **/
@Table(name = "collectionrecords")
@Data
@ToString
public class Collectionrecords {
    @Id
    private Integer corId;
    private String corUsernumber;
    private String corSdnumber;
    private String corReleaseusernumber;
    private Date corDate;
}
