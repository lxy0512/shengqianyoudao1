package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Entity
@Data
public class Post {

    private String number;

    private String username;

    private String title;

    private String type;

    private String content;

    private int state;

    private String images;

    private Date date;

    private int replies;

    private List<reply> reply;

}
