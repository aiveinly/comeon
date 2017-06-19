package com.planet.quoquotation.service;

import com.planet.common.sms.SmsService;
import com.planet.idrule.service.IdRuleService;
import com.planet.message.dao.MessageBatchMapper;
import com.planet.message.dao.MessageMapper;
import com.planet.message.domain.Message;
import com.planet.message.domain.MessageBatch;
import com.planet.message.service.MessageBatchService;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.ordpreorder.domain.OrdPreOrder;
import com.planet.ordpreorder.service.OrdPreOrderService;
import com.planet.quoquotation.dao.QuoQuotationMapper;
import com.planet.quoquotation.domain.QuoQuotation;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.vo.QuoQuotationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Li on 2016/2/16.
 */
@Service
@Transactional
public class QuoQuotationServiceimpl implements QuoQuotationService{

    private static final Logger logger = LoggerFactory.getLogger(QuoQuotationServiceimpl.class);

    @Autowired
    private QuoQuotationMapper quoQuotationMapper;

    @Autowired
    private OrdOrderService ordOrderService;

    @Autowired
    private OrdPreOrderService ordPreOrderService;

    @Autowired
    private IdRuleService idRuleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageBatchService messageBatchService;

    @Autowired
    private SmsService smsService;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String qid) throws Exception {
        int i = 0;
        i = quoQuotationMapper.deleteByPrimaryKey(qid);
        if(i==0){
            logger.info("删除报价单失败");
        }else if(i==1){
            logger.info("删除报价单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(QuoQuotation quoQuotation) throws Exception {
        int i = 0;
        i = quoQuotationMapper.insert(quoQuotation);
        if(i==0){
            logger.info("生成报价单失败");
        }else if(i==1){
            logger.info("生成报价单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(QuoQuotation quoQuotation) throws Exception {
        int i = 0;
        OrdOrder ordOrder = new OrdOrder();
        ordOrder.setOid(quoQuotation.getOid());
        ordOrder.setStatus(2);
        //更新订单表状态为2（报价生成）
        i = ordOrderService.updateByPrimaryKeySelective(ordOrder);
        if(i ==0){
            logger.info("更新订单状态失败。。。");
            return 0;
        }
        logger.info("更新订单状态成功");
        i = 0;
        i = quoQuotationMapper.insertSelective(quoQuotation);
        if (i==0){
            logger.info("更新报价单状态失败");
        }else{
            logger.info("更新报价单状态成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public QuoQuotation selectByPrimaryKey(String qid) throws Exception {
        return quoQuotationMapper.selectByPrimaryKey(qid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(QuoQuotation quoQuotation) throws Exception {
        int i = 0;
        i = quoQuotationMapper.updateByPrimaryKeySelective(quoQuotation);
        if(i==0){
            logger.info("更新报价单失败");
        }else if(i==1){
            logger.info("更新报价单成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(QuoQuotation quoQuotation) throws Exception {
        int i = 0;
        i = quoQuotationMapper.updateByPrimaryKey(quoQuotation);
        if(i==0){
            logger.info("更新报价单失败");
        }else if(i==1){
            logger.info("更新报价单成功");
        }
        return i;
    }

    /**
     * 根据Qid查询报价单详情
     * @param qid
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public QuoQuotationVo selectByqId(String qid) throws Exception {
        QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
        QuoQuotation quoQuotation = new QuoQuotation();
        List<OrdPreOrder> ordPreOrders = new ArrayList<>();
        quoQuotation = quoQuotationMapper.selectByPrimaryKey(qid);
        quoQuotationVo.setQuoQuotation(quoQuotation);
        ordPreOrders = ordPreOrderService.selectSalesPreOrderByOid(quoQuotation.getOid());
        quoQuotationVo.setOrdPreOrder(ordPreOrders);
        return quoQuotationVo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateBySelective(QuoQuotationVo quoQuotationVo) throws Exception {
        int i = 0;
        List<OrdPreOrder> ordPreOrders = new ArrayList<>();
        i = quoQuotationMapper.updateByPrimaryKeySelective(quoQuotationVo.getQuoQuotation());
        ordPreOrders = quoQuotationVo.getOrdPreOrder();
        for (Iterator iterator = ordPreOrders.iterator();iterator.hasNext();){
            int j=0;
            OrdPreOrder ordPreOrder = new OrdPreOrder();
            ordPreOrder = (OrdPreOrder) iterator.next();
            i = ordPreOrderService.updateByPrimaryKeySelective(ordPreOrder);
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public QuoQuotationVo insertSelectQuo(String oid, String createId) throws Exception {
        int i = 0;
        int userid=0;
        OrdOrder ordOrder = new OrdOrder();
        List<OrdPreOrder> ordPreOrders = new ArrayList<>();
        List<OrdPreOrder> checkquo = new ArrayList<>();
        List<OrdPreOrder> ordPreOrders1 = new ArrayList<>();
        QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
        QuoQuotation quoQuotation = new QuoQuotation();
        MessageBatch messageBatch = new MessageBatch();
        Message message = new Message();
        checkquo = ordPreOrderService.selectByOid(oid);
        String qid = null;
        UserAgent userAgent=null;

        if(quoQuotationMapper.selectByPrimaryKey(checkquo.get(0).getQid())==null){
            qid = idRuleService.selectByNumName("Q");
            ordOrder.setOid(oid);
            ordOrder.setStatus(1);
            ordOrder.setQid(qid);
            //更新订单表状态为1（报价中）
            i = ordOrderService.updateByPrimaryKeySelective(ordOrder);
            if(i ==0){
                logger.info("更新订单状态失败。。。");
                return quoQuotationVo;
            }
            logger.info("更新订单状态成功");
            i = 0;
            userid=Integer.valueOf(ordOrderService.selectByOid(oid).getOrdOrder().getUid());
            quoQuotation.setQid(qid);
            quoQuotation.setOid(oid);
            quoQuotation.setVip(userService.selectByPrimaryKey(userid).getVip());
            quoQuotation.setUid(Integer.valueOf(ordOrderService.selectByOid(oid).getOrdOrder().getUid()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());
            Date date = sdf.parse(time);
            quoQuotation.setCreatedate(date);
            quoQuotation.setStatus(0);  //status:0报价单产生
            quoQuotation.setCreateid(Integer.valueOf(createId));
            i = quoQuotationMapper.insertSelective(quoQuotation);
            if (i==0){
                logger.info("更新报价单状态失败");
                throw new RuntimeException("更新报价单状态失败");
            }else{

                logger.info("更新报价单状态成功");
            }
            //更新预订单状态操作
            ordPreOrders1 =ordPreOrderService.selectByOid(oid);
            for (Iterator iterator = ordPreOrders1.iterator();iterator.hasNext();){
                int j=0;
                OrdPreOrder ordPreOrder = new OrdPreOrder();
                ordPreOrder = (OrdPreOrder) iterator.next();
                ordPreOrder.setStatus(1);//更新预订单状态为1，1：待确认
                ordPreOrder.setQid(qid);
                i = ordPreOrderService.updateByPrimaryKeySelective(ordPreOrder);
            }
            Map<String,Object> insertMsgMap = new HashMap<>();
            insertMsgMap.put("message",oid+"订单，报价单已生成。");
            insertMsgMap.put("mType","0");
            insertMsgMap.put("receiver",Integer.valueOf(ordOrderService.selectByOid(oid).getOrdOrder().getUid()));
            insertMsgMap.put("sender",0);
            insertMsgMap.put("stStatus",1);

            //  报价单生成后  发送短信提示
            userAgent= userService.selectByPrimaryKey(userid);
            if (!"".equals(userAgent.getTel())&& (userAgent.getTel() != null) && !userAgent.getTel().isEmpty() )
            {
                smsService.sendSms("您的订单"+oid+"已完成报价，请及时查看。关注微信公众号：kunmajidian，每日特价、产品动态，尽在坤玛",userAgent.getTel());
            }

            int m = messageBatchService.insertMessage(insertMsgMap);
            if(m==0){
                logger.info("报价单生成发送消息失败");
                throw new RuntimeException("报价单生成发送消息失败");
            }

            QuoQuotation quoQuotation1 = new QuoQuotation();
            quoQuotation1 = quoQuotationMapper.selectByPrimaryKey(qid);
            quoQuotationVo.setQuoQuotation(quoQuotation1);
            ordPreOrders = ordPreOrderService.selectByOid(oid);
            quoQuotationVo.setOrdPreOrder(ordPreOrders);

        }else{
            QuoQuotation quoQuotation1 = new QuoQuotation();
            quoQuotation1 = quoQuotationMapper.selectByPrimaryKey(checkquo.get(0).getQid());
            quoQuotationVo.setQuoQuotation(quoQuotation1);
            ordPreOrders = ordPreOrderService.selectByOid(oid);
            quoQuotationVo.setOrdPreOrder(ordPreOrders);
        }
        return quoQuotationVo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public QuoQuotationVo SelectQuo(String oid, String createId) throws Exception {
        int i = 0;
        OrdOrder ordOrder = new OrdOrder();
        List<OrdPreOrder> ordPreOrders = new ArrayList<>();
        List<OrdPreOrder> checkquo = new ArrayList<>();
        List<OrdPreOrder> ordPreOrders1 = new ArrayList<>();
        QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
        QuoQuotation quoQuotation = new QuoQuotation();
        MessageBatch messageBatch = new MessageBatch();
        Message message = new Message();
        checkquo = ordPreOrderService.selectByOid(oid);
        String qid = null;
        QuoQuotation quoQuotation1 = new QuoQuotation();
        quoQuotation1 = quoQuotationMapper.selectByPrimaryKey(checkquo.get(0).getQid());
        quoQuotationVo.setQuoQuotation(quoQuotation1);
        ordPreOrders = ordPreOrderService.selectByOid(oid);
        quoQuotationVo.setOrdPreOrder(ordPreOrders);
        return quoQuotationVo;
    }
}
