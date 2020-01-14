package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

/**
 * @Description
 * @Author wt
 * @data
 */
@Data
public class reply {

    private String number;

    private String username;

    private String aimnumber;

    private String aimusername;

    private String content;

    private String images;

    private Date date;

    private int stand;

}
