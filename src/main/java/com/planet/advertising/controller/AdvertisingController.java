package com.planet.advertising.controller;

import com.planet.advertising.domain.Advertising;
import com.planet.advertising.service.AdvertisingService;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.prodict.domain.ProDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 广告图片Controller
 * Created by aiveily on 2017/6/8.
 */
@Controller
@RequestMapping("/advertising")
public class AdvertisingController {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisingController.class);

    @Autowired
    private AdvertisingService advertisingService;


    @RequestMapping("/toAdvertising")
    private ModelAndView toAdvertising(){
        return  new ModelAndView("/advertising/advertising");
    }

    @RequestMapping("/list")
    @ResponseBody
    private Map list(){
        Map<String,Object> model = new HashMap<>();
        List<Advertising> list = new ArrayList<>();
        try{
            list= advertisingService.selectAdvertisingList(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        Map<String,Object> resultmap = new HashMap<>();
        resultmap.put("list",list);
        model.put("code", 200);
        model.put("success", "ok");
        model.put("result", resultmap);
        return model;
    }

    @RequestMapping("/listAdvertising")
    @ResponseBody
    private Map listAdvertising(int page, int rows){
        Map<String,Object> model = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Pagination pagination = null;
        List<Advertising> list = new ArrayList<>();
        try{
            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            list= advertisingService.listPageAdvertising(map);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        model.put("rows", list);
        model.put("total", pagination.getCount());
        return model;
    }

    @RequestMapping("/addAdvertising")
    @ResponseBody
    public Map addAdvertising(HttpServletRequest request,@ModelAttribute("advertising") Advertising advertising) {
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;
        try {
            advertising.setCreatedate(new Date());
            success = advertisingService.insertSelective(request,advertising);
            if (success == 1) {
                msg = "添加成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "添加失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

    @RequestMapping("/editAdvertising")
    @ResponseBody
    public Map editAdvertising(HttpServletRequest request,@ModelAttribute("advertising") Advertising advertising) {
        Map<String, Object> model = new HashMap<String, Object>();
        int success = 0;
        String msg = "修改失败";
        try {
            success = advertisingService.updateByPrimaryKeySelective(request,advertising);
            if (success == 1) {
                msg = "修改成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "修改失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/deletAdvertisin")
    @ResponseBody
    public Map deletAdvertisin(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "删除失败";
        String id = null;
        try {
            id = request.getParameter("id");
            success = advertisingService.deleteByPrimaryKey(id!=null?Integer.parseInt(id):null);
            if (success == 1) {
                msg = "删除成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "删除失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }
}
