package com.planet.proproduct.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.proproduct.domain.ProProduct;
import com.planet.proproduct.service.ProProductService;
import com.planet.vo.ProductAllVo;
import com.planet.vo.ProductDetailVo;
import com.planet.vo.ProductListBgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品
 * Created by Li on 2016/1/24.
 */
@Controller
@RequestMapping("/product")
public class ProductListController {

    private Logger logger = LoggerFactory.getLogger(ProductListController.class);

    @Autowired
    private ProProductService proProductService;


    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> ProductList(int page, int rows, int type, @RequestParam(value = "keywords", required = false) String keywords) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        List<ProductAllVo> productAllVos = new ArrayList<>();
        Pagination pagination = new Pagination(rows, page);
        map.put("pagination", pagination);
        map.put("ptype", type);
        map.put("productname", keywords);
        productAllVos = proProductService.listPageselectProductSaleList(map);
        Map<String,Object> resultmap = new HashMap<>();
        resultmap.put("productList",productAllVos);
        resultmap.put("count",pagination.getCount());
        model.put("code", 200);
        model.put("success", "ok");
        model.put("result", resultmap);
        return model;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Map<String, Object> ProductDetail(String psid) throws Exception {
        Map<String, Object> map = new HashMap();
        Map model = new HashMap<>();
        List<ProductDetailVo> detailVos = new ArrayList();
        map.put("psid", psid);
        detailVos = proProductService.selectProductDetail(map);
        model.put("code", 200);
        model.put("success", "ok");
        model.put("result", detailVos);
        return model;
    }

    @RequestMapping("/dict")
    public Map<String, Object> ProductDict(int type, int tag, String pre) {
        Map map = new HashMap();
        List list = new ArrayList();

        return map;
    }


    /**
     * list request of customer service system
     *
     * @param request
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listProduct")
    @ResponseBody
    public Map listProduct(HttpServletRequest request, int page, int rows) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        List<ProductListBgVo> productListBgVos = null;
        Pagination pagination = null;

        String pid = null;
        String productName = null;
        String brandid = null;
        String seriesid = null;
        String sortid = null;
        try {

            pid = request.getParameter("pid");
            productName = request.getParameter("productName");
            sortid = request.getParameter("sortId");
            seriesid = request.getParameter("seriesId");
            brandid = request.getParameter("brandId");

            if (!"".equals(pid))
                map.put("pid", pid);
            if (!"".equals(productName))
                map.put("productname", productName);
            if (!"".equals(sortid))
                map.put("sortid", sortid);
            if (!"".equals(seriesid))
                map.put("seriesid", seriesid);
            if (!"".equals(brandid))
                map.put("brandid", brandid);


            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            productListBgVos = proProductService.listPageSelectProProduct(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.put("rows", productListBgVos);
        model.put("total", pagination.getCount());
        return model;
    }


    /**
     * enter into product list page of customer service system
     *
     * @return
     */
    @RequestMapping("/toProduct")
    public ModelAndView toProductList() {
        return new ModelAndView("/product/product");
    }

    /**
     * add prodect request of customer service system
     * -@RequestParam("file") MultipartFile[] fileList,
     *
     * @return
     */
    @RequestMapping("/addProduct")
    @ResponseBody
    public Map addProduct(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;
        BigDecimal price = null;
        String productName = null;
        Integer sortId = null;
        Integer seriesId = null;
        Integer brandId = null;
        String describemodel = null;

        try {

            productName = request.getParameter("productName");
            describemodel = request.getParameter("describemodel");
            sortId = Integer.parseInt(request.getParameter("sortId"));
            price =new BigDecimal(request.getParameter("price"));
            seriesId = Integer.parseInt(request.getParameter("seriesId"));
            brandId = Integer.parseInt(request.getParameter("brandId"));


            ProProduct proProduct = new ProProduct();
            proProduct.setProductname(productName);
            proProduct.setDescribemodel(describemodel);
            proProduct.setPrice(price);
            proProduct.setBrandid(brandId);
            proProduct.setSortid(sortId);
            proProduct.setSeriesid(seriesId);
            proProduct.setPid(String.valueOf(System.currentTimeMillis()));

            success = proProductService.insertSelective(proProduct, request);

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


    /**
     * edit prodect request of customer service system
     *
     * @return
     */
    @RequestMapping("/editProduct")
    @ResponseBody
    public Map editProduct(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "修改失败";
        int success = 0;
        String pid = null;
        BigDecimal price = null;
        String productName = null;
        Integer sortId = null;
        Integer seriesId = null;
        Integer brandId = null;
        String describemodel = null;

        try {

            pid = request.getParameter("pid");
            productName = request.getParameter("productName");
            sortId = Integer.parseInt(request.getParameter("sortId"));
            price =new BigDecimal(request.getParameter("price"));
            seriesId = Integer.parseInt(request.getParameter("seriesId"));
            brandId = Integer.parseInt(request.getParameter("brandId"));
            describemodel = request.getParameter("describemodel");

            ProProduct proProduct = new ProProduct();

            proProduct.setPid(pid);
            proProduct.setProductname(productName);
            proProduct.setPrice(price);
            proProduct.setBrandid(brandId);
            proProduct.setSortid(sortId);
            proProduct.setSeriesid(seriesId);
            proProduct.setDescribemodel(describemodel);
            success = proProductService.updateByPrimaryKeySelective(proProduct, request);
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
     * remove prodect request of customer service system
     *
     * @return
     */
    @RequestMapping("/removeProduct")
    @ResponseBody
    public Map removeProduct(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        String msg = "删除失败";
        int success = 0;
        String pid = null;
        try {
            pid = request.getParameter("pid");
            success = proProductService.deleteByPrimaryKey(pid);
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
