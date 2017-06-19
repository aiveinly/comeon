package com.planet.ordorder.controller;

import com.planet.admin.domain.Admin;
import com.planet.admin.service.AdminService;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.common.poi.tools.ExcelUtils;
import com.planet.common.poi.tools.JsGridReportBase;
import com.planet.common.poi.tools.TableData;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.prodict.domain.ProDict;
import com.planet.vo.OrdOrderVo;
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
 * Created by yehao on 2016/2/14.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrdOrderService ordOrderService;


    /**
     * 进入订单列表主页
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/order/orderList");
    }


    /**
     * 查询订单列表
     *
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("/listOrder")
    @ResponseBody
    public Map listOrder(HttpServletRequest request, int rows, int page) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        Pagination pagination = null;
        List<OrdOrderVo> ordOrderVos = new ArrayList<OrdOrderVo>();
        String oid = null;
        String userName = null;
        String status = null;
        String suid = null;
//      非超级管理员 只能查询自己的订单
        Admin admin1 = new Admin();
        admin1 = (Admin) request.getSession().getAttribute("admin");
        if (!"0".equals(admin1.getRole())) {
            suid = admin1.getSuid().toString();

        } else {
            map.put("suid", suid);
        }


        try {
            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);

            oid = request.getParameter("oid");
            userName = request.getParameter("userName");
            status = request.getParameter("status");
            map.put("oid", oid);
            map.put("username", userName);
            map.put("status", status);
            map.put("suid", suid);

            ordOrderVos = ordOrderService.listPageSelectOrder(map);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        model.put("rows", ordOrderVos);
        model.put("total", pagination.getCount());
        return model;
    }


    @RequestMapping("/completeOrder")
    @ResponseBody
    public Map ordComplete(String oid) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "订单提交失败";
        String did = null;
        try {
            success = ordOrderService.firmOrder(oid);
            if (success == 1) {
                msg = "订单提交成功";
            } else if (success == 0) {
                msg = "订单提交成功";

            } else if (success == 2) {
                msg = "订单已完成，请不要重复点击";

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;

    }

    /**
     * 导出
     * @return
     */
    @RequestMapping("/exportOrder")
    @ResponseBody
    public void exportOrder(HttpServletRequest request,HttpServletResponse response){
        try {
            response.setContentType("application/msexcel;charset=utf-8");
            List<Map> orderList=ordOrderService.selectAllOrderToVo(null);
            if(null!=orderList&&orderList.size()>0){
                setExcel(request,response,orderList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
        }
    }

    public void setExcel(HttpServletRequest request,HttpServletResponse response,List<Map> orderList) throws Exception{
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        String headName = "订单列表";
        String excelName="orderList";
        String[] titleNames  = new String[]{"编号","用户名","姓名","是否vip","订单类型","订单状态","预定单数","报价单","创建时间"};
        String[] tableFileds  = new String[]{"oid","username","name","vip","ptype","status","opCount","quoCount","createdateString"};
        TableData td = ExcelUtils.createTableData(orderList, ExcelUtils.createTableHeader(titleNames), tableFileds);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(headName,admin.getUsername(), td,excelName);
    }
}
