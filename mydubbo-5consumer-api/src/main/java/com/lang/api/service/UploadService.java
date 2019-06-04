package com.lang.api.service;

import cn.hutool.core.util.StrUtil;
import com.qiniu.common.Region;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author faraway
 * @date 2019/6/4 15:24
 */
@Service
public class UploadService {

    //七牛分配的的二级域名,使用期限30天,过期将无法通过该域名访问图片
    @Value("${qiniu.domain}")
    private String qnDomainName;

    private String IMAGE_PREFIX = "image/";

    //七牛云存储的用户标识
    @Value("${qiniu.ak}")
    private String accessKey;

    //七牛云储存的密钥
    @Value("${qiniu.sk}")
    private String secketKey;

    //七牛云储存空间
    @Value("${qiniu.bucket}")
    private String bucketName;

    /**
     * 上传核心方法
     */
    public Response uploadToQn(byte[] uploadBytes, String key, String token) throws Exception {
        /*
            Region.region0() 华东
            Region.region1() 华北
            Region.region2() 华南
            Region.regionNa0() 北美
            Region.regionAs0() 新加坡
         */
        Configuration cfg = new Configuration(Region.region1());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager.put(uploadBytes, key, token);
    }

    /**
     *
     * @param fileName 文件名
     * @param type     所属类型：产品图片/用户头像... product/user
     * @param imageFile 文件
     * @throws Exception
     */
    public void uploadProductImg(String fileName, String type, MultipartFile imageFile) throws Exception {
        //用户标识和密钥生成Auth信息
        Auth auth = Auth.create(accessKey, secketKey);
        //生成token信息
        String token = auth.uploadToken(bucketName);
        //相对路径(即七牛内容管理的相对路径)
        String key = StrUtil.builder(IMAGE_PREFIX).append(type).append("/").append(fileName).toString();
        //开始上传-参数:文件的字节码--相对路径--token信息
        uploadToQn(imageFile.getBytes(), key, token);
        //获取图片的绝对路径(可以直接复制打卡图片的地址)
        String location = StrUtil.builder(qnDomainName).append(key).toString();
        System.out.println(location);
    }
}
