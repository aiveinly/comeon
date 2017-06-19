package com.planet.user.controller;

import com.planet.admin.domain.Admin;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.common.poi.tools.ExcelUtils;
import com.planet.common.poi.tools.JsGridReportBase;
import com.planet.common.poi.tools.TableData;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yehao on 2016/1/20.
 */
@Controller
@RequestMapping("/user")
public class UserController {


    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * to user list page
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/user/userList");
    }

    /**
     * get User List
     *
     * @return
     */
    @RequestMapping("/userList")
    @ResponseBody
    public Map getUserList(HttpServletRequest request, int page, int rows) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        String name = null;
        String username = null;
        Pagination pagination = new Pagination(rows, page);
        map.put("pagination", pagination);

        List<UserAgent> userAgents = new ArrayList<UserAgent>();
        try {
            name = request.getParameter("name");
            username = request.getParameter("username");
            map.put("name", name);
            map.put("username", username);
            userAgents = userService.listPageUserAgentAndReferralUname(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        model.put("rows", userAgents);
        model.put("total", pagination.getCount());
        return model;
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map deleteUser(int uid) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        int success = 0;
        String msg = "删除失败";

        try {
            UserAgent userAgent = new UserAgent();
            userAgent.setUid(uid);
            userAgent.setStatus(-1);
            success = userService.updateByPrimaryKeySelective(userAgent);
            if (success == 1) {
                msg = "删除成功";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            success = 0;
            msg = "删除失败";
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Map updateUser(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        int success = 0;
        String msg = "修改失败";
        Integer uid = null;
        String name = null;
        String username = null;
        String password = null;
        String tel = null;
        String remark = null;

        try {

            uid = Integer.parseInt(request.getParameter("uid"));
            name = request.getParameter("name");
            username = request.getParameter("username");
            password = request.getParameter("password");
            tel = request.getParameter("tel");
            remark=request.getParameter("remark");
            UserAgent userAgent = new UserAgent();
            userAgent.setUid(uid);
            userAgent.setName(name);
            userAgent.setUsername(username);
            userAgent.setPassword(password);
            userAgent.setTel(tel);
            userAgent.setRemark(remark);
            success = userService.updateByPrimaryKeySelective(userAgent);
            if (success == 1) {
                msg = "修改成功";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            success = 0;
            msg = "修改失败";
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

    @RequestMapping("/freezeUser")
    @ResponseBody
    public Map freezeUser(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        int success = 0;
        String msg = "冻结失败";
        Integer uid = 0;
        Integer status = 0;


        try {

            uid = Integer.parseInt(request.getParameter("uid"));
            status = Integer.parseInt(request.getParameter("status"));


            UserAgent userAgent = new UserAgent();
            userAgent.setUid(uid);
            if (status == 0)
                userAgent.setStatus(1);
            else
                userAgent.setStatus(0);

            success = userService.updateByPrimaryKeySelective(userAgent);
            if (success == 1) {
                if (status == 0)
                    msg = "解冻成功";
                else
                    msg = "冻结成功";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            success = 0;
            if (status == 0)
                msg = "解冻失败";
            else
                msg = "冻结失败";

        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    /**
     * 导出
     * @return
     */
    @RequestMapping("/exportUser")
    @ResponseBody
    public void exportUser(HttpServletRequest request,HttpServletResponse response){
        try {
            response.setContentType("application/msexcel;charset=utf-8");
            List<Map> userAgentList=userService.selectAllUserAgentToVo(null);
            setExcel(request,response,userAgentList);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }
    }

    public void setExcel(HttpServletRequest request,HttpServletResponse response,List<Map> userAgentList) throws Exception{
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        String headName = "用户列表";
        String excelName="userList";
        String[] titleNames  = new String[]{"用户名","昵称","是否vip","积分","电话","状态","备注","注册日期"};
        String[] tableFileds  = new String[]{"username","name","vip","point","tel","status","remark","logindate"};
        TableData td = ExcelUtils.createTableData(userAgentList, ExcelUtils.createTableHeader(titleNames), tableFileds);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(headName,admin.getUsername(), td,excelName);
    }
}
