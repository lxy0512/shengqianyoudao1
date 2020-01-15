package cn.qiandao.shengqianyoudao.service.impl;

import cn.qiandao.shengqianyoudao.mapper.ImagesMapper;
import cn.qiandao.shengqianyoudao.pojo.Images;
import cn.qiandao.shengqianyoudao.service.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesServiceImpl implements IImagesService {
    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public String getImageAddress(String gameId) {
        Images images = new Images();
        images.setImId(gameId);
        Images reuultImages = imagesMapper.selectOne(images);
        return reuultImages.getImAddress();
    }

    @Override
    public String changeId(int imageName) {
        String imgId ="";
        switch (imageName) {
            case 1:
                imgId = "jn0017";
                break;
            case 2:
                imgId = "jn0018";
                break;
            case 3:
                imgId = "jn0019";
                break;
            case 4:
                imgId = "jn0020";
                break;
            case 5:
                imgId = "jn0021";
                break;
            case 6:
                imgId = "jn0022";
                break;
            case 7:
                imgId = "jn0023";
                break;
            case 8:
                imgId = "jn0024";
                break;
            case 9:
                imgId = "jn0025";
                break;
            case 10:
                imgId = "jn0026";
                break;
            case 11:
                imgId = "jn0027";
                break;
            case 12:
                imgId = "jn0028";
                break;
            case 13:
                imgId = "jn0029";
                break;
            default:
                break;
        }
        String imageAddress = getImageAddress(imgId);
        return imageAddress;
    }
}
