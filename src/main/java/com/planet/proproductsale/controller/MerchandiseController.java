package com.planet.proproductsale.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.proproductsale.domain.ProProductSale;
import com.planet.proproductsale.service.ProProductSaleService;
import com.planet.vo.ProductSaleInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/2/3.
 */
@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {


    private Logger logger = LoggerFactory.getLogger(MerchandiseController.class);

    @Autowired
    private ProProductSaleService proProductSaleService;


    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/merchandise/merchandise");
    }


    @RequestMapping("/listMerchandise")
    @ResponseBody
    public Map listMerchandise(HttpServletRequest request, int rows, int page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        List<ProductSaleInfoVo> productSaleInfoVos = null;
        Pagination pagination = null;

        String psid = null;
        String productName = null;
        String ptype = null;
        String sortid = null;
        String seriesid = null;
        String brandid = null;


        try {


            psid = request.getParameter("psid");
            productName = request.getParameter("productName");
            ptype = request.getParameter("pType");
            sortid = request.getParameter("sortId");
            seriesid = request.getParameter("seriesId");
            brandid = request.getParameter("brandId");


            if (!"".equals(psid))
                map.put("psid", psid);
            if (!"".equals(productName))
                map.put("productname", productName);
            if (!"".equals(ptype))
                map.put("ptype", ptype);
            if (!"".equals(sortid))
                map.put("sortid", sortid);
            if (!"".equals(seriesid))
                map.put("seriesid", seriesid);
            if (!"".equals(brandid))
                map.put("brandid", brandid);


            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            productSaleInfoVos = proProductSaleService.listPageSelectProduct(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.put("rows", productSaleInfoVos);
        model.put("total", pagination.getCount());
        return model;
    }


    @RequestMapping("/addMerchandise")
    @ResponseBody
    public Map addListMerchandise(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "添加失败";

        String pid = null;
        String productName = null;
        Integer stock = null;
        Integer pType = null;
        BigDecimal price = null;
        String remark = null;
        Integer sn = null;


        try {
            pid = request.getParameter("productId");
            stock = Integer.parseInt(request.getParameter("stock"));
            pType = Integer.parseInt(request.getParameter("pType"));
            if (request.getParameter("price") != null && !request.getParameter("price").equals(""))
                price = new BigDecimal(request.getParameter("price"));
            remark = request.getParameter("remark");
            if (request.getParameter("sn") != null && !request.getParameter("sn").equals(""))
                sn = Integer.parseInt(request.getParameter("sn"));

            ProProductSale proProductSale = new ProProductSale();
            proProductSale.setPid(pid);
            proProductSale.setStock(stock);
            proProductSale.setPtype(pType);
            proProductSale.setPrice(price);
            proProductSale.setRemark(remark);
            proProductSale.setSn(sn);

            success = proProductSaleService.insertSelective(proProductSale);


            if (success == 1) {
                msg = "添加成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            msg = "添加失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/removeMerchandise")
    @ResponseBody
    public Map removeMerchandise(String psid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "删除失败";


        try {

            success = proProductSaleService.deleteByPrimaryKey(psid);
            if (success == 1) {
                msg = "删除失败";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            msg = "删除失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/editMerchandise")
    @ResponseBody
    public Map editMerchandise(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "修改失败";

        String psid = null;
//        String pid = null;
        String stock = null;
        String pType = null;
        String sortId = null;
//        String brandId = null;
//        String seriesId = null;
        String price = null;
//        String remark = null;
        String sn = null;

        try {


            psid = request.getParameter("psid");
            stock = request.getParameter("stock");
//            pType = request.getParameter("pType");
            price = request.getParameter("price");
//            remark = request.getParameter("remark");
//            pid = request.getParameter("pid");
            sn = request.getParameter("sn");
//            sortId = request.getParameter("sortId");
//            brandId = request.getParameter("brandId");
//            seriesId = request.getParameter("seriesId");

            ProProductSale proProductSale = new ProProductSale();
            proProductSale.setPsid(psid);
            proProductSale.setStock(Integer.parseInt(stock));
            if (price != null && !"".equals(price))
                proProductSale.setPrice(new BigDecimal(price));
//            if (pType != null)
//                proProductSale.setPtype(Integer.parseInt(pType));

            if (sn != null) {
                proProductSale.setSn(Integer.parseInt(sn));
            }
            success = proProductSaleService.updateByPrimaryKeySelective(proProductSale);

            if (success == 1) {
                msg = "修改成功";
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            msg = "修改失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

}



