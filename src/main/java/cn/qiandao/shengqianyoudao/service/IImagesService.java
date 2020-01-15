package cn.qiandao.shengqianyoudao.service;

/**
 * zrf images表
 */
public interface IImagesService {
    //获取图片地址
    String getImageAddress(String gameId);
    String changeId(int imageName);
}
