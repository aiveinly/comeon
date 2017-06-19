package com.planet.proproductsale.service;

import com.planet.proproductsale.domain.ProProductSale;
import com.planet.vo.ProductSaleInfoVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/2.
 */
public interface ProProductSaleService {

    int deleteByPrimaryKey(String psid) throws Exception;

    int insert(ProProductSale proProductSale);

    int insertSelective(ProProductSale proProductSale) throws Exception;

    ProProductSale selectByPrimaryKey(String psid) throws Exception;

    int updateByPrimaryKeySelective(ProProductSale proProductSale) throws Exception;

    int updateByPrimaryKey(ProProductSale proProductSale) throws Exception;

    //查询销售商品列表（后台）
    List<ProductSaleInfoVo> listPageSelectProduct(Map map) throws Exception;

    //查询销售产品是否使用产品主表数据 for pro_product del
    int selectByPid(Map<String,Object> map) throws Exception;
}
