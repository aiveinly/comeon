package com.planet.ordpreorder.service;

import com.planet.idrule.service.IdRuleService;
import com.planet.ordpreorder.dao.OrdPreOrderMapper;
import com.planet.ordpreorder.domain.OrdPreOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li on 2016/2/4.
 */
@Service
@Transactional
public class OrdPreOrderServiceimpl implements OrdPreOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrdPreOrderServiceimpl.class);


    @Autowired
    private OrdPreOrderMapper ordPreOrderMapper;

    @Autowired
    private IdRuleService idRuleService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String poid)throws Exception {
        int i = 0;
        i=ordPreOrderMapper.deleteByPrimaryKey(poid);
        if(i==0){
            logger.info("删除预订单失败");
        }else if (i == 1){
            logger.info("删除预订单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(OrdPreOrder ordPreOrder)throws Exception {
        int i = 0;
        i = ordPreOrderMapper.insert(ordPreOrder);
        if(i==0){
            logger.info("插入预订单失败");
        }else{
            logger.info("插入预订单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(OrdPreOrder ordPreOrder)throws Exception {
        int i =0;
        ordPreOrder.setPoid(idRuleService.selectByNumName("PD"));
        i=ordPreOrderMapper.insertSelective(ordPreOrder);
        if(i==0){
            logger.info("生成预订单失败");
        }else if (i == 1){
            logger.info("生成预订单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrdPreOrder selectByPrimaryKey(String poid)throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(OrdPreOrder ordPreOrder)throws Exception {
        int i = 0;
        i = ordPreOrderMapper.updateByPrimaryKeySelective(ordPreOrder);
        if (i == 0){
            logger.info("更新预订单失败");
        }else if (i == 1){
            logger.info("更新预订单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(OrdPreOrder ordPreOrder)throws Exception {
        return 0;
    }




    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OrdPreOrder> selectSalesPreOrderByOid(String oid) throws Exception {
        return ordPreOrderMapper.selectSalesPreOrderByOid(oid);
    }





    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OrdPreOrder> selectByOid(String oid) throws Exception {
        return ordPreOrderMapper.selectByOid(oid);
    }

    /**
     * 根据Rid查询预订单状态为0的数量
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int selectOrdPreOrderCount(String rid) throws Exception {
        int i = 0;
        List<OrdPreOrder> ordPreOrders = new ArrayList<>();
        ordPreOrders = ordPreOrderMapper.selectOrdPreOrderCount(rid);
        if(ordPreOrders==null&&"".equals(ordPreOrders)){
            i=0;
        }else{
            i = ordPreOrders.size();
        }
        return i;
    }
}
