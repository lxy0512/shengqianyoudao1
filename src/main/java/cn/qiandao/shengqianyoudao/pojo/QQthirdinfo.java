package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lxy
 * @date 2020/1/12 0012 20:43
 **/
@Table(name = "qqthirdinfo")
@Data
public class QQthirdinfo {
    @Id
    private Integer qqId;
    private String qqOpenid;
    private String qqToken;
    private Integer qqDate;
    private String qqUsernumber;
    private String qqRefresh;

}
