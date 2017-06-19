package com.planet.utils.OSS;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Created by junhua on 2016/3/19.
 */
public class QiNiuFileSystem {
    private static final Logger logger = LoggerFactory.getLogger(QiNiuFileSystem.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = "eMi1ayo5A6K1fHqUOLof27DR2jSnMP0fyVuDH8dX";

    private static String SECRET_KEY = "DY4YvBVRchdgFeHRW1CJRo8ufpbG2o8PqFiURX44";
    //要上传的空间
    private static  String bucketname = "image";

    public static List<String> QiNiuuploadFile(Map<String, MultipartFile> fileMap) {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //创建上传对象
        UploadManager uploadManager = new UploadManager();
        List urllists = new ArrayList();
        Response res = null;
        try {
            Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                if (mFile.isEmpty()) {

                } else {
                    //String key1 = UUID.randomUUID()+ mFile.getOriginalFilename();
                    String  fileName= mFile.getOriginalFilename();
                    String fileSuffix = fileName.substring(fileName.indexOf("."),fileName.length());
                    logger.info("文件名后缀"+fileSuffix);
                    String key1 = UUID.randomUUID()+fileSuffix;
                    //调用put方法上传
                    logger.info("开始调用上传方法");
                    res = uploadManager.put(mFile.getBytes(), key1, auth.uploadToken(bucketname));
                    String path = res.jsonToMap().get("key").toString();
                    logger.info("文件名称。。。。。"+path);
                    urllists.add(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urllists;
    }

    /**
     * 该方法废弃
     * @param list
     * @return
     */
    @Deprecated
    public static List<String> QiNiuuploadFile(List list) {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //创建上传对象
        UploadManager uploadManager = new UploadManager();
        List urllists = new ArrayList();
        Response res = null;
        //上传文件名
        String key = null;
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                File uploadFile = (File) it.next();
               // key = UUID.randomUUID() + uploadFile.getName();
                key = UUID.randomUUID() + uploadFile.getName();
                //调用put方法上传
                res = uploadManager.put(uploadFile, key, auth.uploadToken(bucketname));
                String path = res.jsonToMap().get("key").toString();
                logger.info("上传文件名"+path);
                urllists.add(path);
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return urllists;
    }




}
