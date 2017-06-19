package com.planet.utils.OSS;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This sample demonstrates how to get started with basic requests to Aliyun OSS
 * using the OSS SDK for Java.
 */

/**
 * Created by junhua on 2016/3/18.
 */
public class AliyunOss {
    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "2L9YPGdujcb2aOXB";
    private static String accessKeySecret = "RpyX7hM02XFCqOJmXQ4ZBoQga8TxLy";

    private static OSSClient client = null;

    public static  List<String>  AliyunFileUpload(Map<String, MultipartFile> fileMap) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "kmtest";
        String key = null;

        List urllists = new ArrayList();
        try {
        Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                key = UUID.randomUUID()+mFile.getOriginalFilename();
                client.putObject(bucketName, key,mFile.getInputStream());
                String path =key;
                urllists.add(path);
            }
            client.shutdown();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        return  urllists;
    }

    public static  List<String>  AliyunFileUpload(List list) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "kmtest";
        String key = null;
        List urllists = new ArrayList();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                File uploadFile = (File)it.next();
                InputStream input = new FileInputStream(uploadFile);
                key = UUID.randomUUID()+uploadFile.getName();
                client.putObject(bucketName, key,input);
                String path = "http://kmtest.oss-cn-shanghai.aliyuncs.com/"+key;
                urllists.add(path);
            }
            client.shutdown();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        return  urllists;
    }


}
