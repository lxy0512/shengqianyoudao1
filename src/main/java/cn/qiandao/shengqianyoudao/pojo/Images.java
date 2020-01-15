package cn.qiandao.shengqianyoudao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 图片表 images
 * zrf
 */
@Data
@Table(name = "images")
public class Images {
    @Column(name = "id")
    private Integer imagesId;
    @Column(name = "im_id")
    private String imId;
    @Column(name = "im_address")
    private String imAddress;
}
