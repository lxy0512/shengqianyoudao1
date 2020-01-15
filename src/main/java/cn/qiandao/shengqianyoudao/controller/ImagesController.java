package cn.qiandao.shengqianyoudao.controller;

import cn.qiandao.shengqianyoudao.pojo.Result;
import cn.qiandao.shengqianyoudao.service.IImagesService;
import cn.qiandao.shengqianyoudao.util.FastDFSClient;
import io.swagger.annotations.ApiImplicitParam;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 1.9 zrf
 */
@RestController
@RequestMapping("/getImage")
public class ImagesController {
    @Autowired
    private IImagesService imagesService;

    /**
     * 获取发布技能游戏参数
     * @param skillId 技能id
     * @return
     */
    @ApiImplicitParam(name = "skillId",value = "技能id",required = true,dataType = "string",paramType = "path")
    @GetMapping("getImage/{skillId}")
    public String getImage(@PathVariable(name = "skillId") String skillId){
        int skill = Integer.parseInt(skillId);
        String result = imagesService.changeId(skill);
        return result;
    }


/*//    @Value("${FILE_SERVER_URL}")
    private String file_server_url;
    @RequestMapping("/upload")
    public Result upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();//获取文件名
        String extName=originalFilename.substring( originalFilename.lastIndexOf(".")+1);//得到扩展名
        try {
            FastDFSClient client=new FastDFSClient("classpath:resources/fdfs_client.conf");
            String fileId = client.uploadFile(file.getBytes(), extName);
            String url=file_server_url+fileId;//图片完整地址
            System.out.println(url);
            return new Result(true, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }*/

    public static void main(String[] args) throws Exception {
        ClientGlobal.init("E:\\shengqianyoudao\\src\\main\\resources\\fdfs_client.conf");
        // 2、创建一个 TrackerClient 对象。直接 new 一个。
        TrackerClient trackerClient = new TrackerClient();
        // 3、使用 TrackerClient 对象创建连接，获得一个 TrackerServer 对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        // 4、创建一个 StorageServer 的引用，值为 null
        StorageServer storageServer = null;
        // 5、创建一个 StorageClient 对象，需要两个参数 TrackerServer 对象、StorageServer的引用
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 6、使用 StorageClient 对象上传图片。
        //扩展名不带“.”
        String[] strings = storageClient.upload_file("F:/deskimages/desk.jpg", "jpg",
                null);
        // 7、返回数组。包含组名和图片的路径。
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
