package com.planet.proproductsale.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.proproductsale.domain.ProProductSale;
import com.planet.vo.ProductSaleInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProProductSaleMapper extends MybatisMapper{
    int deleteByPrimaryKey(String psid);

    int insert(ProProductSale proProductSale);

    int insertSelective(ProProductSale proProductSale);

    ProProductSale selectByPrimaryKey(String psid);

    int updateByPrimaryKeySelective(ProProductSale proProductSale);

    int updateByPrimaryKey(ProProductSale proProductSale);

    //查询销售商品列表（后台）
    List<ProductSaleInfoVo> listPageSelectProduct(Map map);

    //查询销售产品是否使用产品主表数据 for pro_product del
    int selectByPid(Map<String,Object> map);

}