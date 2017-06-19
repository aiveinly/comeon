package com.planet.ordpreorder.controller;

import com.planet.admin.domain.Admin;
import com.planet.admin.service.AdminService;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.ordpreorder.domain.OrdPreOrder;
import com.planet.ordpreorder.service.OrdPreOrderService;
import com.planet.prodict.domain.ProDict;
import com.planet.proproduct.domain.ProProduct;
import com.planet.vo.OrdOrderDetailVo;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/2/15.
 */
@Controller
@RequestMapping("/preOrder")
public class PreOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PreOrderController.class);


    @Autowired
    private OrdPreOrderService preOrderService;
    @Autowired
    private OrdOrderService ordOrderService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/index")
    public ModelAndView index(String oid, String rid) {
        //init
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("oid", oid);
        model.put("rid", rid);
        return new ModelAndView("/order/preorderlist", model);
    }

    @RequestMapping("/listPreOrder")
    @ResponseBody
    public Map listPreOrder(String oid) {

        //init
        Map<String, Object> model = new HashMap<String, Object>();
        List<OrdPreOrder> ordPreOrders = null;

        try {
            ordPreOrders = preOrderService.selectByOid(oid);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ordPreOrders = null;
        }
        model.put("rows", ordPreOrders);
        return model;
    }

    @RequestMapping("/addPreOrder")
    @ResponseBody
    public Map addPreOrder(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;

        String oid = null;
        String rid = null;
        String productName = null;
        BigDecimal price = null;
        Integer qty = null;
        String sortName = null;
        String seriesName = null;
        String brandName = null;
        String modelName = null;
        String remark = null;
        String deliveryTime=null;

        try {
            oid = request.getParameter("oid");
            rid = request.getParameter("rid");
            productName = request.getParameter("productName");
            sortName = request.getParameter("sortName");
            if (request.getParameter("price") != null)
                price =new BigDecimal(request.getParameter("price"));
            seriesName = request.getParameter("seriesName");
            brandName = request.getParameter("brandName");
            qty = Integer.parseInt(request.getParameter("qty"));
            modelName = request.getParameter("modelName");
            remark = request.getParameter("remark");
            deliveryTime= request.getParameter("deliveryTime");

            OrdPreOrder ordPreOrder = new OrdPreOrder();
            ordPreOrder.setProductname(productName);
            ordPreOrder.setOid(oid);
            ordPreOrder.setQty(qty);
            ordPreOrder.setPrice(price);
            ordPreOrder.setBrandname(brandName);
            ordPreOrder.setSeriesname(seriesName);
            ordPreOrder.setSortname(sortName);
            ordPreOrder.setModelname(modelName);
            ordPreOrder.setRemark(remark);
            ordPreOrder.setRid(rid);
            ordPreOrder.setDeliverytime(deliveryTime);

            success = preOrderService.insertSelective(ordPreOrder);

            Admin admin1=new Admin();
            admin1= (Admin) request.getSession().getAttribute("admin");

            OrdOrder ordOrder = new OrdOrder();
            ordOrder.setOid(oid);
            ordOrder.setStatus(1);
            ordOrder.setSuid(admin1.getSuid().toString());
            ordOrderService.updateByPrimaryKeySelective(ordOrder);



            logger.info("订单" + oid + "添加一条预订单");
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

    @RequestMapping("/editPreOrder")
    @ResponseBody
    public Map editPreOrder(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "修改失败";
        int success = 0;

        String poid = null;
        String oid = null;
        String rid = null;
        String productName = null;
        BigDecimal price = null;
        Integer qty = null;
        String sortName = null;
        String seriesName = null;
        String brandName = null;
        String modelName = null;
        String remark = null;
        String deliveryTime=null;

        try {
            poid = request.getParameter("poid");
            oid = request.getParameter("oid");
            rid = request.getParameter("rid");
            productName = request.getParameter("productName");
            sortName = request.getParameter("sortName");
            price =new BigDecimal(request.getParameter("price"));
            seriesName = request.getParameter("seriesName");
            brandName = request.getParameter("brandName");
            qty = Integer.parseInt(request.getParameter("qty"));
            modelName = request.getParameter("modelName");
            remark = request.getParameter("remark");
            deliveryTime = request.getParameter("deliveryTime");

            OrdPreOrder ordPreOrder = new OrdPreOrder();
            ordPreOrder.setPoid(poid);
            ordPreOrder.setProductname(productName);
            ordPreOrder.setOid(oid);
            ordPreOrder.setQty(qty);
            ordPreOrder.setPrice(price);
            ordPreOrder.setBrandname(brandName);
            ordPreOrder.setSeriesname(seriesName);
            ordPreOrder.setSortname(sortName);
            ordPreOrder.setModelname(modelName);
            ordPreOrder.setRemark(remark);
            ordPreOrder.setRid(rid);
            ordPreOrder.setDeliverytime(deliveryTime);

            success = preOrderService.updateByPrimaryKeySelective(ordPreOrder);
            logger.info("订单" + oid + "的一条预订单被修改了");
            if (success == 1) {
                msg = "修改成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "修改失败";
            success = 0;
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    /**
     * remove preorder
     *
     * @param request
     * @return
     */
    @RequestMapping("/removePreOrder")
    @ResponseBody
    public Map removePreOrder(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "删除失败";
        int success = 0;
        String poid = null;
        try {

            poid = request.getParameter("poid");
            success = preOrderService.deleteByPrimaryKey(poid);
            if (success == 1) {
                msg = "删除成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "删除失败";
            success = 0;
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

}
