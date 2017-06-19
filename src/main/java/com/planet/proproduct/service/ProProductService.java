package com.planet.proproduct.service;

import com.planet.proproduct.domain.ProProduct;
import com.planet.vo.ProductAllVo;
import com.planet.vo.ProductDetailVo;
import com.planet.vo.ProductListBgVo;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/21.
 */
public interface ProProductService  {
    int deleteByPrimaryKey(String pid) throws Exception;

    int insert(ProProduct proProduct) throws Exception;

    int insertSelective(ProProduct proProduct,HttpServletRequest request) throws Exception;

    int insertSelective(ProProduct proProduct) throws Exception;

    ProProduct selectByPrimaryKey(String pid) throws Exception;

    int updateByPrimaryKeySelective(ProProduct proProduct) throws Exception;

    //更新产品信息
    int updateByPrimaryKeySelective(ProProduct proProduct,HttpServletRequest request) throws Exception;

    int updateByPrimaryKey(ProProduct proProduct) throws Exception;

    List<ProductListBgVo> listPageSelectProProduct(Map map) throws Exception;

    //查询销售的商品列表
    List<ProductAllVo> listPageselectProductSaleList(Map map) throws Exception;

    //查询销售的商品详情
    List<ProductDetailVo> selectProductDetail(Map map) throws Exception;

    //通过1 品类 2 品牌 3 系列 三个参数检查是否有商品在使用 for del dictionary
    int selectById(Map<String,Object> map) throws Exception;
}
