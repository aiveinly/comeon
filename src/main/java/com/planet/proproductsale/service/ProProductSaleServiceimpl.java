package com.planet.proproductsale.service;

import com.planet.idrule.service.IdRuleService;
import com.planet.proproductsale.dao.ProProductSaleMapper;
import com.planet.proproductsale.domain.ProProductSale;
import com.planet.vo.ProductSaleInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/2.
 */
@Service
@Transactional
public class ProProductSaleServiceimpl implements ProProductSaleService {
    private static final Logger logger = LoggerFactory.getLogger(ProProductSaleServiceimpl.class);

    @Autowired
    private ProProductSaleMapper proProductSaleMapper;

    @Autowired
    private IdRuleService idRuleService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String psid)throws Exception {
        int i = 0;
        i = proProductSaleMapper.deleteByPrimaryKey(psid);
        if(i==0){
            logger.info("删除销售产品失败");
        }else if(i==1){
            logger.info("删除销售产品成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(ProProductSale proProductSale) {
        int i = 0;
        i = proProductSaleMapper.insert(proProductSale);
        if(i==0){
            logger.info("增加发布产品失败");
        }else if(i==1){
            logger.info("增加发布产品成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(ProProductSale proProductSale)throws Exception {
        int i = 0;
        Date date = new Date();
        proProductSale.setPsid(idRuleService.selectByNumName("PS"));
        proProductSale.setStatus(1);
        proProductSale.setSaledate(date);
        i = proProductSaleMapper.insertSelective(proProductSale);
        if (i == 0) {
            logger.info("增加发布产品失败");
        } else if (i == 1) {
            logger.info("增加发布产品成功");
        }
        return i;

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProProductSale selectByPrimaryKey(String psid)throws Exception {
        return proProductSaleMapper.selectByPrimaryKey(psid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ProProductSale proProductSale)throws Exception {
        int i = 0;
        i=proProductSaleMapper.updateByPrimaryKeySelective(proProductSale);
        if (i == 0) {
            logger.info("更新发布产品失败");
        } else if (i == 1) {
            logger.info("更新发布产品成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(ProProductSale proProductSale)throws Exception {
        int i = 0;
        i = proProductSaleMapper.updateByPrimaryKey(proProductSale);
        if(i==0){
            logger.info("更新发布产品失败");
        }else if(i==1){
            logger.info("更新发布产品成功");
        }
        return i;
    }

    /**
     * 查询发布信息
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProductSaleInfoVo> listPageSelectProduct(Map map)throws Exception {
        return proProductSaleMapper.listPageSelectProduct(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public  int selectByPid(Map<String,Object> map) throws Exception {
        return proProductSaleMapper.selectByPid(map);
    }
}
