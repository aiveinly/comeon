package com.planet.reqrequirement.controller;


import com.planet.reqrequirement.domain.ReqRequirement;
import com.planet.reqrequirement.service.ReqrequirementService;
import com.planet.sysfile.domain.SysFile;
import com.planet.sysfile.service.SysFileService;
import com.planet.utils.FileOperateUtil;
import com.planet.vo.ReqrequirementVo;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Li on 2016/1/21.
 */
@Controller
@RequestMapping("/req")
public class ReqrequirementController {

    private static final Logger logger = LoggerFactory.getLogger(ReqrequirementController.class);

    @Autowired
    private ReqrequirementService reqrequirementService;

    @Autowired
    private SysFileService sysFileService;


    @RequestMapping("/create1")
    @ResponseBody
    public Map<String, Object> createReq(HttpServletRequest request) throws Exception {
        //初始化状态值
        int i = 0;
        Map<String, Object> map = new HashMap<>();
        ReqRequirement reqRequirement = null;
        String json = request.getParameter("extraFiles");
        if (json == null) {
            request.getParameter("");
            reqRequirement.setPsid(request.getParameter("psid"));
            reqRequirement.setUid(Integer.valueOf(request.getParameter("uid")));
            reqRequirement.setPtype(Integer.valueOf(request.getParameter("pType")));
            reqRequirement.setProductname(request.getParameter("productName"));
            reqRequirement.setSortname(request.getParameter("sortName"));
            reqRequirement.setBrandname(request.getParameter("brandName"));
            reqRequirement.setModelname(request.getParameter("modelName"));
            reqRequirement.setPrice(new BigDecimal(request.getParameter("price")));
            reqRequirement.setQty(Integer.valueOf(request.getParameter("qty")));
            reqRequirement.setRemark(request.getParameter("remark"));
            reqRequirement.setSeriesname(request.getParameter("seriesName"));
            i = reqrequirementService.insertSelective(reqRequirement);
        } else {
            //将jsonArray转换成List
            JSONArray jsonarray = JSONArray.fromObject(json);
            List<File> files = (List<File>) JSONArray.toCollection(jsonarray);
            List<String> urllist = new ArrayList<>();
            urllist = FileOperateUtil.upload(files);
            SysFile sysFile = new SysFile();
            int fileId = sysFileService.insert(sysFile, urllist);
            reqRequirement.setPsid(request.getParameter("psid"));
            reqRequirement.setUid(Integer.valueOf(request.getParameter("uid")));
            reqRequirement.setPtype(Integer.valueOf(request.getParameter("pType")));
            reqRequirement.setProductname(request.getParameter("productName"));
            reqRequirement.setSortname(request.getParameter("sortName"));
            reqRequirement.setBrandname(request.getParameter("brandName"));
            reqRequirement.setModelname(request.getParameter("modelName"));
            reqRequirement.setPrice(new BigDecimal(request.getParameter("price")));
            reqRequirement.setQty(Integer.valueOf(request.getParameter("qty")));
            reqRequirement.setRemark(request.getParameter("remark"));
            reqRequirement.setSeriesname(request.getParameter("seriesName"));
            reqRequirement.setFileid(fileId);
            i = reqrequirementService.insertSelective(reqRequirement);
        }
        map.put("code", i);
        map.put("success", "ok");
        return map;
    }


    @RequestMapping("/index")
    public ModelAndView index(String rid) {
        //init
        Map<String, Object> model = new HashMap<>();
        ReqrequirementVo reqrequirementVo = null;
        try {
            reqrequirementVo = reqrequirementService.selectByRid(rid);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

//        List<SysFile> sysFiles = new ArrayList<>();
//        SysFile f1 = new SysFile();
//        f1.setFileurl("http://www.baidu.com/link?url=DpREbRhh1TBUnJHlK4CwXHwvS24obGrc-XWIhr8REPYgh8QekJL_J48CnwpGYWlrcyUyhFhASHRCZc74dJWt1bhzgCugpy1wMUvXZEhRlE_&wd=&eqid=d57ae93f000099830000000456eb7e52");
//        SysFile f2 = new SysFile();
//        f2.setFileurl("http://www.baidu.com/link?url=DpREbRhh1TBUnJHlK4CwXHwvS24obGrc-XWIhr8REPYgh8QekJL_J48CnwpGYWlrcyUyhFhASHRCZc74dJWt1bhzgCugpy1wMUvXZEhRlE_&wd=&eqid=d57ae93f000099830000000456eb7e52");
//        sysFiles.add(f1);
//        sysFiles.add(f2);
//        reqrequirementVo.setSysFiles(sysFiles);

        model.put("dataModel", reqrequirementVo.getReqRequirement());
        model.put("fileModel", reqrequirementVo.getSysFiles());
        model.put("fileNum", reqrequirementVo.getSysFiles().size());
        return new ModelAndView("/order/requirment2", model);
    }

    @RequestMapping("/product-req")
    @ResponseBody
    public Map<String, Object> productReq(@RequestBody ReqRequirement requirement) {
        Map<String, Object> result = new HashMap<>();
        String msg = "ok";
        int respCode = 200;
        try {
            if ((requirement.getUid()==null) && ("".equals(requirement.getUid()))) {
                throw new RuntimeException("需求提交失败,请退出后重新提交！");

            }

            requirement.setRid(UUID.randomUUID().toString());
            requirement.setStatus(0);
            int code = reqrequirementService.insert(requirement);
            if (code == 0) {
                throw new RuntimeException("需求提交失败！");
            }
        } catch (Exception ex) {
            logger.error("需求提交失败", ex);
            msg = "需求提交失败";
            respCode = 500;
        }
        result.put("code", respCode);
        result.put("message", msg);
        result.put("result", null);
        return result;
    }

    @RequestMapping("/req-file-upload")
    @ResponseBody
    public Map<String,Object> reqFileUpload(HttpServletRequest request){
        Map<String, Object> resp = new HashMap<>();
        int code = 200;
        String msg = "ok";
        int fileListId = -1;
        try{
            List<String> urlLists = FileOperateUtil.upload(request);
            fileListId = sysFileService.insert(new SysFile(), urlLists);
        }catch (Exception e){
            logger.error("上传文件失败:",e);
            code = 500;
            msg = "文件上传失败";
        }
        resp.put("code", code);
        resp.put("message",msg);
        resp.put("result", fileListId);
        return resp;
    }

    @RequestMapping("/getRequirement")
    @ResponseBody
    public Map<String, Object> getRequirement(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        List result = new ArrayList();
        String rid = null;
        ReqRequirement reqRequirement = new ReqRequirement();
        rid = request.getParameter("rid");
        try {
            reqRequirement = reqrequirementService.selectByPrimaryKey(rid);
            if (reqRequirement != null) {

                map.put("code", 200);
                map.put("message", "ok");
                map.put("result", reqRequirement);
            } else {
                map.put("code", 400);
                map.put("message", "获取错误！");
                map.put("result", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "获取错误！");
            map.put("result", result);
            return map;
        }

        return map;
    }


}
