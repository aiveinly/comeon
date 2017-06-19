package com.planet.test.controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.planet.utils.FileOperateUtil;
import com.planet.utils.OSS.QiNiuFileSystem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.planet.utils.OSS.AliyunOss.AliyunFileUpload;


/**
 * Created by Li on 2016/1/15.
 */
@Controller
@RequestMapping(value="fileOperate")
public class FileOperateController {
private static Logger logger = LoggerFactory.getLogger(FileOperateController.class);
    @RequestMapping(value="upload")
    public String upload(HttpServletRequest request){
        MultipartHttpServletRequest mRequest =null;
        Map<String, MultipartFile> fileMap=null;
        mRequest= (MultipartHttpServletRequest) request;
        try {
            fileMap= mRequest.getFileMap();
            QiNiuFileSystem.QiNiuuploadFile(fileMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:list";
    }
    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request){
        init(request);
        request.setAttribute("map", getMap());
        return new ModelAndView("fileOperate/list");
    }
    @RequestMapping(value="download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        init(request);
        try {
            String downloadfFileName = request.getParameter("filename");
            downloadfFileName = new String(downloadfFileName.getBytes("iso-8859-1"),"utf-8");
            String fileName = downloadfFileName.substring(downloadfFileName.indexOf("_")+1);
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            fileName = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
//            FileOperateUtil.download(downloadfFileName, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init(HttpServletRequest request) {
        if(FileOperateUtil.FILEDIR == null){
            FileOperateUtil.FILEDIR = request.getSession().getServletContext().getRealPath("/") + "file/";
        }
    }
    private Map<String, String> getMap(){
        Map<String, String> map = new HashMap<String, String>();
        File[] files = new File(FileOperateUtil.FILEDIR).listFiles();
        if(files != null){
            for (File file : files) {
                if(file.isDirectory()){
                    File[] files2 = file.listFiles();
                    if(files2 != null){
                        for (File file2 : files2) {
                            String name = file2.getName();
                            map.put(file2.getParentFile().getName() + "/" + name, name.substring(name.lastIndexOf("_")+1));
                        }
                    }
                }
            }
        }
        return map;
    }

}
