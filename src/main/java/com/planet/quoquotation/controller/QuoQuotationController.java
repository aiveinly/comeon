package com.planet.quoquotation.controller;

import com.planet.admin.domain.Admin;
import com.planet.common.sms.SmsService;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.ordpreorder.domain.OrdPreOrder;
import com.planet.quoquotation.domain.QuoQuotation;
import com.planet.quoquotation.service.QuoQuotationService;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.vo.QuoQuotationVo;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yehao on 2016/2/19.
 */
@Controller
@RequestMapping("/quoquotation")
public class QuoQuotationController {

    Logger logger = LoggerFactory.getLogger(QuoQuotationController.class);

    @Autowired
    private QuoQuotationService quoQuotationService;

    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrdOrderService orderService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, String oid) {
        //init
        Map<String, Object> model = new HashMap<String, Object>();
        QuoQuotationVo quoQuotationVo = null;
        QuoQuotation quoQuotation = null;
        List<OrdPreOrder> ordPreOrders = null;

        Integer createId = null;


        try {

            createId = ((Admin) (request.getSession().getAttribute("admin"))).getSuid();

            quoQuotationVo = quoQuotationService.insertSelectQuo(oid, String.valueOf(createId));
            if (quoQuotationVo.getQuoQuotation() != null) {
                OrdOrder order = new OrdOrder();
                order.setOid(quoQuotationVo.getQuoQuotation().getOid());
                order.setStatus(2);
                orderService.updateByPrimaryKeySelective(order);



            }


            quoQuotation = quoQuotationVo.getQuoQuotation();

            ordPreOrders = quoQuotationVo.getOrdPreOrder();


        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        model.put("quoData", quoQuotation);
        model.put("orderData", ordPreOrders);
        model.put("preOrderNum", ordPreOrders.size());
        return new ModelAndView("/order/baojiadan", model);
    }


    @RequestMapping("/editQuo")
    @ResponseBody
    public Map editQuo(HttpServletRequest request, QuoQuotation quoQuotation, @RequestParam("kaipiaojias") String[] kaipiaojias, @RequestParam("preOrderIds") String[] preOrderIds) {

        //ini
        Map<String, Object> model = new HashMap<String, Object>();

        int success = 0;
        String msg = "保存失败";


        try {
            if (quoQuotation.getStatus() > 0) {

                msg = "报价单'已确认'或'已完成'不允许保存";

            } else {

                QuoQuotationVo quoQuotationVo = new QuoQuotationVo();

                //获得所有 开票价 和对应的 预订单id
                List<OrdPreOrder> preOrders = new ArrayList<OrdPreOrder>();
                for (int i = 0; i < kaipiaojias.length; i++) {
                    if (!"".equals(kaipiaojias[i])) {
                        OrdPreOrder ordPreOrder = new OrdPreOrder();
                        ordPreOrder.setPoid(preOrderIds[i]);

                        ordPreOrder.setQuoPrice(new BigDecimal(kaipiaojias[i]));
                        preOrders.add(ordPreOrder);
                    }
                }


                quoQuotationVo.setOrdPreOrder(preOrders);
                quoQuotationVo.setQuoQuotation(quoQuotation);


                success = quoQuotationService.updateBySelective(quoQuotationVo);


            }


            if (success == 1) {
                msg = "保存成功";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            msg = "保存失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/existBaojiadan")
    @ResponseBody
    public Map existBaojiadan(HttpServletRequest request, String oid) {
        //ini
        Map<String, Object> model = new HashMap<String, Object>();

        int success = 0;
        String msg = "查询错误";

        try {
            QuoQuotationVo vo = quoQuotationService.SelectQuo(oid, String.valueOf(((Admin) (request.getSession().getAttribute("admin"))).getSuid()));
            if (vo.getQuoQuotation() != null) {
                success = 1;
                msg = "存在报价单！";
            } else {
                success = 2;
                msg = "报价单不存在，是否新建？";
            }
        } catch (Exception e) {
            logger.error("查询报价单是否存在错误！", e);
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

}
