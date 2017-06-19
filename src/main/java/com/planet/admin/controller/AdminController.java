package com.planet.admin.controller;

import com.planet.admin.domain.Admin;
import com.planet.admin.service.AdminService;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.vip.domain.Vip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/2/18.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    private AdminService adminService;


    /**
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView index() {
        return new ModelAndView("/login/login");
    }

    @RequestMapping("/menu")
    public ModelAndView menu(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<>();
        model.put("admin", request.getSession().getAttribute("admin"));
        return new ModelAndView("/menu/menu", model);

    }

    @RequestMapping("/index")
    public ModelAndView adminList() {
        return new ModelAndView("/admin/admin_list");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return new ModelAndView("/login/login");
    }


    @RequestMapping("/submitLogin")
    @ResponseBody
    public Map submitLogin(HttpServletRequest request, String userName, String password) {
        //init
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = null;

        try {

            Admin admin = adminService.selectByUserName(userName);
            if (admin.getUsername().equals(userName.trim()) && admin.getPassword().equals(password.trim())) {
                request.getSession().setAttribute("admin", admin);
                success = 1;
            }
        } catch (Exception e) {
            logger.error("查询admin错误！", e);
        }


        model.put("success", success);
        model.put("msg", msg);
        return model;
    }


    @RequestMapping("/listAdmin")
    @ResponseBody
    public Map listAdmin(int rows, int page) {

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Pagination pagination = null;
        List<Admin> admins = new ArrayList<>();

        try {

            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            admins = adminService.listPageSelect(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        model.put("rows", admins);
        model.put("total", pagination.getCount());
        return model;

    }


    @RequestMapping("/addAdmin")
    @ResponseBody
    public Map addAdmin(HttpServletRequest request) {


        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;
        String username = null;
        String name = null;
        String password = null;
        String role = null;
        String tel = null;

        Admin a = (Admin) request.getSession().getAttribute("admin");
        if ("0".equals(a.getRole())) {
            try {

                username = request.getParameter("username");
                name = request.getParameter("name");
                password = request.getParameter("password");
                role = request.getParameter("role");
                tel = request.getParameter("tel");

                Admin admin = new Admin();
                admin.setUsername(username);
                admin.setName(name);
                admin.setPassword(password);
                admin.setRole(role);
                admin.setTel(tel);

                success = adminService.insertSelective(admin);
                logger.info("添加了一个管理员账户");
                if (success == 1) {
                    msg = "修改成功";
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                success = 0;
            }
        } else {
            msg = "权限错误！";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/removeAdmin")
    @ResponseBody
    public Map removeAdmin(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "删除失败";
        String suid = null;

        Admin a = (Admin) request.getSession().getAttribute("admin");
        if ("0".equals(a.getRole())) {
            try {
                suid = request.getParameter("suid");
                success = adminService.deleteByPrimaryKey(Integer.parseInt(suid));
                if (success == 1) {
                    msg = "删除成功";
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                success = 0;
                msg = "删除失败";
            }
        } else {
            msg = "权限错误！";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

    @RequestMapping("/editAdmin")
    @ResponseBody
    public Map editAdmin(Admin admin) {
        //init
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "修改失败";

        try {
            success = adminService.updateByPrimaryKeySelective(admin);
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

}


