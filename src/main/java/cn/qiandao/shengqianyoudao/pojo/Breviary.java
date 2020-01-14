package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @Description
 * @Author wt
 * @data
 */
@Data
@Entity
public class Breviary {

    private String number;

    private String username;

    private String images;

    private String code;
}
