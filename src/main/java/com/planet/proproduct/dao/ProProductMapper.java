package com.planet.proproduct.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.proproduct.domain.ProProduct;
import com.planet.vo.ProductAllVo;
import com.planet.vo.ProductDetailVo;
import com.planet.vo.ProductListBgVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProProductMapper extends MybatisMapper {
    int deleteByPrimaryKey(String pid);

    int insert(ProProduct proProduct);

    int insertSelective(ProProduct proProduct);

    ProProduct selectByPrimaryKey(String pid);

    int updateByPrimaryKeySelective(ProProduct proProduct);

    int updateByPrimaryKey(ProProduct proProduct);

    List<ProductListBgVo> listPageSelectProProduct(Map map);

    //查询销售的商品列表
    List<ProductAllVo> listPageselectProductSaleList(Map map);

    //查询销售的商品详情
    List<ProductDetailVo> selectProductDetail(Map map);

    //通过1 品类 2 品牌 3 系列 三个参数检查是否有商品在使用 for del dictionary
    int selectById(Map<String,Object> map);

}