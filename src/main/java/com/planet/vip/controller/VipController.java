package com.planet.vip.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.common.sms.SmsService;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.vip.domain.Vip;
import com.planet.vip.service.VipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yehao on 2016/2/24.
 */
@Controller
@RequestMapping("/apply")
public class VipController {


    private Logger logger = LoggerFactory.getLogger(VipController.class);

    @Autowired
    private VipService vipService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;


    @RequestMapping("/index")
    public ModelAndView index() {


        return new ModelAndView("/user/vip_apply");
    }

    @RequestMapping("/listApply")
    @ResponseBody
    public Map listApply(int page, int rows) {

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Pagination pagination = null;
        List<Vip> vipList = new ArrayList<>();
        try {

            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            vipList = vipService.listPageSelect(map);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        model.put("rows", vipList);
        model.put("total", pagination.getCount());
        return model;
    }


    @RequestMapping("/deal")
    @ResponseBody
    public Map dealApply(int id, int status, int uid, Integer applytype) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "处理失败";
        String stxt="";
        int success = 0;
        try {

            Vip vip = new Vip();
            vip.setId(id);
            vip.setUid(uid);
            vip.setStatus(status);
            vip.setApplytype(applytype);
            vip.setUpdatedate(new Date());
            success = vipService.updateByPrimaryKeySelective(vip);
            if (success == 1) {
                msg = "处理成功";
                if (status==1){
                    stxt="恭喜您，您的VIP申请已通过审核，请您退出平台并重新登陆，以获取更多权限。";
                }else{
                    stxt="很遗憾，您的VIP申请没有通过审核，您可以完善资料后重新提交。如需获取更多帮助，请致电4006590690转0。";
                }
                UserAgent userAgent=new UserAgent();
                userAgent=userService.selectByPrimaryKey(uid);
                smsService.sendSms(stxt,userAgent.getTel());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "处理失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/addVipApply")
    @ResponseBody
    public Map dealApply(HttpServletRequest request, Vip vip) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;
        try {
            success = vipService.addVip(vip);
            if (success == 1) {
                msg = "添加成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "添加失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

}

