package com.planet.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.common.sms.SmsService;
import com.planet.invoice.domain.Invoice;
import com.planet.invoice.service.InvoiceService;
import com.planet.message.domain.Message;
import com.planet.message.service.MessageBatchService;
import com.planet.message.service.MessageService;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.ordpreorder.domain.OrdPreOrder;
import com.planet.ordpreorder.service.OrdPreOrderService;
import com.planet.quoquotation.domain.QuoQuotation;
import com.planet.quoquotation.domain.QuotationStatus;
import com.planet.quoquotation.service.QuoQuotationService;
import com.planet.reqrequirement.service.ReqrequirementService;
import com.planet.suggest.domain.Suggest;
import com.planet.suggest.service.SuggestService;
import com.planet.sysfile.domain.SysFile;
import com.planet.sysfile.service.SysFileService;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.vip.domain.Vip;
import com.planet.vip.service.VipService;
import com.planet.vo.*;
import net.sf.json.JSONObject;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人中心
 * Created by Li on 2016/1/24.
 */
@Controller
@RequestMapping("/personal")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrdOrderService ordOrderService;

    @Autowired
    private OrdPreOrderService ordPreOrderService;

    @Autowired
    private QuoQuotationService quoQuotationService;

    @Autowired
    private VipService vipService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private MessageBatchService messageBatchService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SuggestService suggestService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ReqrequirementService reqrequirementService;

    @Autowired
    private SysFileService sysFileService;

    /**
     * 用户注册
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> userRegister(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> point = new HashMap<>();//积分用
        UserAgent userAgent = new UserAgent();
        UserAgent selectUserAgent = new UserAgent();
        UserAgent referralUser ;   //推荐人
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        int i = 0;
        try {
            String uname = request.getParameter("uname");
            String name = request.getParameter("name");
            String referralcode = request.getParameter("referralcode");
            selectUserAgent = userService.selectByUserName(uname);
            if (selectUserAgent != null) {
                model.put("code", 400);
                model.put("success", "该账号已注册");
                map.put("result", model);
                return map;
            } else {

                String pwd = request.getParameter("pwd");
                userAgent.setPassword(pwd);
                userAgent.setUsername(uname);
                userAgent.setName(name);
                userAgent.setTel(uname);
                userAgent.setVip(0);
                userAgent.setStatus(1);
                Date date = sdf.parse(time);
                userAgent.setLogindate(date);
                userAgent.setLastlogindate(date);
                if(!"".equals(referralcode)){
                    UserAgent ug = userService.selectUserAgentByCode(referralcode);
                    userAgent.setReferraluid(null != ug ? ug.getUid() : null);
                }
                logger.info("登陆时间    " + date);
                i = userService.insertSelective(userAgent);
                if (i == 0) {
                    model.put("code", 400);
                    model.put("success", "注册失败");
                } else if (i == 1) {
                    model.put("code", 200);
                    model.put("success", "ok");
                    selectUserAgent = userService.selectByUserName(uname);
                    int jifen = 200;    //  注册送积分     默认200 分
                    point.put("uid", selectUserAgent.getUid());
                    point.put("message", "注册送" + jifen + "积分");
                    point.put("referralcode", referralcode);
                    point.put("point", jifen);
                    point.put("level","register");
                    userService.updatePoint(point);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.put("code", 400);
            model.put("success", "注册失败");
            map.put("result", model);
            return map;
        }
        map.put("code", 200);
        map.put("message", "ok");
        map.put("result", model);
        return map;
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map_point= new HashMap<>();
        UserAgent userAgent = null;
        int code;
        String message;
        try {
            userAgent = userService.selectByUserName(username);
            if (userAgent != null && password.endsWith(userAgent.getPassword())) {
                Date curDate=new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.format(curDate);
                Date lastDate = userAgent.getLastlogindate();
                if (lastDate!=null){
                    long intervalMilli = curDate.getTime() - lastDate.getTime();
                    int iday=(int) (intervalMilli / (24 * 60 * 60 * 1000));
                    if (iday>1){
                        map_point.put("uid", userAgent.getUid());
                        map_point.put("message", "连续登陆送积分");
                        map_point.put("level","login");
                        userService.updatePoint(map_point);
                    }
                }
                code = 200;
                message = "ok";
            } else {
                throw new RuntimeException("用户名或密码错误！");
            }
        } catch (Exception e) {
            code = 400;
            message = e.getMessage();
        }
        map.put("code", code);
        map.put("message", message);
        map.put("result", userAgent);
        return map;
    }


    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public Map<String, Object> getUser(String username) {
        Map <String,Object>resp = new HashMap<>();
        String message="ok";
        Integer code=200;
        UserAgent userAgent = null;
        try {
            userAgent = userService.selectByUserName(username);
            Assert.notNull(userAgent,"用户信息不存在！");
        } catch (Exception e) {
            logger.error("获取用户信息错误",e);
            code = 400;
            message = e.getMessage();
        }
        resp.put("code", code);
        resp.put("message", message);
        resp.put("result", userAgent);
        return resp;
    }

    /**
     * 获取我的预订单详情
     *
     * @param oid
     * @return
     */
    @RequestMapping("/getDetailOrder")
    @ResponseBody
    public Map<String, Object> getDetailOrder(String oid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        OrdOrderDetailVo ordOrderDetailVo = new OrdOrderDetailVo();
        try {
            ordOrderDetailVo = ordOrderService.selectByOid(oid);
            if (ordOrderDetailVo != null) {
                res.put("ordOrder", ordOrderDetailVo.getOrdOrder());
                res.put("ordPreOrders", ordOrderDetailVo.getOrdPreOrders());
                String rid = ordOrderDetailVo.getOrdOrder().getRid();
                ReqrequirementVo reqrequirementVo = null;
                try {
                     reqrequirementVo = reqrequirementService.selectByRid(rid);
                } catch (Exception e) {
                    //需求单不影响订单的显示,故不做处理
                    logger.error("获取需求单失败", e);
                }
                res.put("requirement", reqrequirementVo);
                map.put("code", 200);
                map.put("message", "ok");
                map.put("result", res);
            } else {
                map.put("code", 400);
                map.put("message", "获取错误！");
                map.put("result", null);
            }
        } catch (Exception e) {
            logger.error("查询订单详情出错:", e);
            map.put("code", 400);
            map.put("message", "获取错误！");
            map.put("result", null);
            return map;
        }

        return map;
    }

    /**
     *  
     * 获取订单列表
     *
     * @param username 用户id
     * @return
     */
    @RequestMapping("/getOrderlist")
    @ResponseBody
    public Map<String, Object> getOrder(HttpServletRequest request, String username, String rows, String page) {
        Map<String, Object> map = new HashMap();
        Map<String, Object> model = new HashMap();
        List<OrdOrderVo> result = new ArrayList();
        Pagination pagination = null;
        try {
            if (username == null && "".equals(username)) {
                model.put("code", 400);
                model.put("message", "获取订单出错！");
                model.put("result", result);
                return map;
            } else {
                map.put("username", username);
                if (rows != null && page != null) {
                    pagination = new Pagination(Integer.valueOf(rows), Integer.valueOf(page));
                } else {
                    rows = "10";
                    page = "1";
                    pagination = new Pagination(Integer.valueOf(rows), Integer.valueOf(page));
                }

                map.put("pagination", pagination);
                result = ordOrderService.listPageSelectOrder(map);
                Map<String,Object> orderVo = new HashMap<>();
                orderVo.put("orderList",result);
                orderVo.put("count",pagination.getCount());
                model.put("result", orderVo);
                model.put("code", 200);
                model.put("message", "ok！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            model.put("code", 400);
            model.put("message", "获取订单出错！");
            model.put("result", result);
            return model;
        }
        return model;
    }

    /**
     * 删除预订单
     *
     * @param poid
     * @return
     */
    @RequestMapping("/delDetailOrder")
    @ResponseBody
    public Map<String, Object> delDetailOrder(String poid) {
        Map map = new HashMap();
        return map;
    }

    /**
     * 确认预订单
     *
     * @param poid
     * @return
     */
    @RequestMapping("/affirmDetailOrder")
    @ResponseBody
    public Map<String, Object> affirmDetailOrder(String poid, String qty, String quoPrice) {
        Map map = new HashMap();
        OrdPreOrder ordPreOrder = new OrdPreOrder();
        List result = new ArrayList();
        int i = 0;
        ordPreOrder.setPoid(poid);
        ordPreOrder.setQty(Integer.valueOf(qty));
        ordPreOrder.setQuoPrice(new BigDecimal(quoPrice));
        ordPreOrder.setStatus(2);//设置状态为2，预订单已确认
        try {
            i = ordPreOrderService.updateByPrimaryKeySelective(ordPreOrder);
            if (i == 0) {
                map.put("code", 400);
                map.put("message", "确认预订单失败！");
                map.put("result", result);
            } else if (i == 1) {
                map.put("code", 200);
                map.put("message", "确认预订单成功！");
                map.put("result", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "系统异常！");
            map.put("result", result);
        }

        return map;
    }


    /**
     * 验证码
     *
     * @param mobile 手机号
     * @return
     */
    @RequestMapping("/identifycode")
    @ResponseBody
    public Map<String, Object> identifyCode(HttpServletRequest request, String mobile) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        List result = new ArrayList();
        //修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后获取
        String apikey = "dc1579f1c0d69634115334a906b8e602";
        /**************** 使用智能匹配模版接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        int x = (int) (Math.random() * 9000 + 1000);
        logger.info("验证码是： " + x);
        String text = "您的验证码是："+x+"，请在页面中输入以完成下一步操作。关注微信公众号：kunmajidian，工业机电自动化 一站式采购中心。";
        try {

            String info = smsService.sendSms(text, mobile);
            //       String info = SmsUtils.sendSms(apikey, text, mobile);

            logger.info("短信消息返回消息" + info);
            //       JSONObject jsonObject = JSONObject.fromObject(info);
            //        if ("0".equals(jsonObject.get("code").toString())) {
            map.put("code", 200);
            map.put("message", "ok");
            //model.put("identifyCode", x);
            //result.add(x);
            map.put("result", x);
            HttpSession session = request.getSession();
            session.setAttribute("identifyCode", String.valueOf(x));
            logger.info("写入session验证码的值为" + String.valueOf(x));
/*            } else {
                map.put("code", 400);
                map.put("message", "失败，请重新发送！");
                map.put("result", result);
            }*/
        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "失败，请重新发送！");
            map.put("result", result);
            return map;
        }
        return map;
    }

    /**
     * 修改密码
     *
     * @param username     用户名
     * @param pw           密码
     * @param identifyCode 验证码
     * @return
     */
    @RequestMapping("/changepw")
    @ResponseBody
    public Map<String, Object> changePassWord(HttpServletRequest request, String username, String pw, String identifyCode) {
        Map<String, Object> resp = new HashMap<>();
        String identifyCodes = request.getSession().getAttribute("identifyCode").toString();
        int code;
        String message;
        try {
            if (identifyCode == null) {
                throw new RuntimeException("请输入验证码！");
            }
            if (!identifyCodes.equals(identifyCode)) {
                throw new RuntimeException("验证码不正确！");
            }
            UserAgent userAgent;
            try {
                userAgent = userService.selectByUserName(username);
                if (userAgent == null) {
                    throw new RuntimeException();
                }
            } catch (Exception e1) {
                logger.error("查询用户信息出错", e1);
                throw new RuntimeException("查询用户信息出错!");
            }

            userAgent.setPassword(pw);
            try {
                Integer status = userService.updateByPrimaryKeySelective(userAgent);
                if (status == 0) {
                    throw new RuntimeException();
                }
            } catch (Exception e2) {
                logger.error("更新密码出错", e2);
                throw new RuntimeException("更新密码出错!");
            }
            code = 200;
            message = "更新成功！";
        } catch (Exception e) {
            code = 500;
            message = e.getMessage();
        } finally {
            request.getSession().removeAttribute("identifyCode");
        }
        resp.put("code", code);
        resp.put("message", message);
        resp.put("result", null);
        return resp;
    }

    /**
     * 检查用户是否存在
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Map<String, Object> checkUser(String username) {
        Map<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        UserAgent userAgent = new UserAgent();
        try {
            userAgent = userService.selectByUserName(username);
            if (userAgent == null) {
                map.put("code", 200);
                map.put("message", "该用户未被注册");
                map.put("result", list);
            } else {
                map.put("code", 400);
                map.put("message", "该用户已被注册");
                map.put("result", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "查询出错");
            map.put("result", list);
            return map;
        }

        return map;
    }

    /**
     * 查询报价单
     *
     * @param qid
     * @return
     */
    @RequestMapping("/selectquo")
    @ResponseBody
    public Map<String, Object> selectQuo(String qid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
        List result = new ArrayList();
        try {
            quoQuotationVo = quoQuotationService.selectByqId(qid);
            if (quoQuotationVo != null) {
                //result.add(quoQuotationVo);
                model.put("code", 200);
                model.put("message", "查询成功");
                model.put("result", quoQuotationVo);
            } else {
                model.put("code", 400);
                model.put("message", "查询失败");
                model.put("result", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            model.put("code", 400);
            model.put("message", "查询失败");
            model.put("result", result);
        }
        return model;
    }

    /**
     * 修改报价单
     *
     * @param quoQuoVo
     * @return
     */
    @RequestMapping("/updatequo")
    @ResponseBody
    public Map<String, Object> updateQuo(@RequestBody QuoQuoVo quoQuoVo) {
        Map<String, Object> resp = new HashMap<>();
        int code;
        String message;
        try {
            List<OrdPreOrder> preOrders = new ArrayList<>();
            int iid = quoQuoVo.getIid();
            List list = quoQuoVo.getOrdpreorderid();
            JSONArray jsonArray = new JSONArray(list);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.getJSONObject(i));
                QuoPriceVo o = (QuoPriceVo) JSONObject.toBean(jsonObject, QuoPriceVo.class);
                OrdPreOrder ordPreOrder = new OrdPreOrder();
                ordPreOrder.setPoid(o.getPoid());
                if (o.getQuoPrice() != null) {
                    ordPreOrder.setQuoPrice(new BigDecimal(o.getQuoPrice()));
                }
                if (o.getQty() != null) {
                    ordPreOrder.setQty(Integer.valueOf(o.getQty()));
                }
                preOrders.add(ordPreOrder);
            }
            QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
            QuoQuotation quotation = new QuoQuotation();
            quotation.setQid(quoQuoVo.getQid());
            quotation.setUid(quoQuoVo.getUid());
            quotation.setRemark(quoQuoVo.getJianyi());
            quotation.setStatus(QuotationStatus.CONFIRM.getValue());

            quoQuotationVo.setOrdPreOrder(preOrders);
            quoQuotationVo.setQuoQuotation(quotation);
            int i = quoQuotationService.updateBySelective(quoQuotationVo);

            Invoice invoice = invoiceService.selectByPrimaryKey(iid);
            quotation.setBank(invoice.getBank());
            quotation.setAccount(invoice.getAccount());
            quotation.setTax(invoice.getTax());
            quotation.setCompanytel(invoice.getCompanytel());
            quotation.setReceivertel(invoice.getReceivertel());
            quotation.setReceiver(invoice.getReceiver());
            quotation.setAddress(invoice.getAddress());
            quotation.setBackdate(new Date());
            quoQuotationService.updateByPrimaryKeySelective(quotation);
            OrdOrder ordordertmp = new OrdOrder();
            ordordertmp.setStatus(3);   //报价确认中

            ordordertmp.setOid(quoQuotationService.selectByPrimaryKey(quoQuoVo.getQid()).getOid());
            ordOrderService.updateByPrimaryKeySelective(ordordertmp);

            if (i == 1) {
                code = 200;
                message = "ok";
                logger.info("修改报价单成功");
            } else {
                logger.info("修改报价单失败");
                throw new RuntimeException();
            }
        } catch (Exception e) {
            logger.info(e.toString());
            code = 400;
            message = "修改报价单失败!";
            logger.info("修改报价单失败");
        }
        resp.put("code", code);
        resp.put("message", message);
        resp.put("result", null);
        return resp;
    }

    /**
     * vip申请
     *
     * @param vip
     * @return
     */
    @RequestMapping("/applyvip")
    @ResponseBody
    public Map<String, Object> applyVip(@RequestBody Vip vip) {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        try {
            if (vip.getUid() != null) {
                //根据file1查询
                List<SysFile> SysFile = sysFileService.selectByFileId(Integer.valueOf(vip.getFile1()));
                if(SysFile.size()>0){
                    vip.setFile1(SysFile.get(0).getFileurl());
                }else{
                    //不做处理
                }
                i = vipService.insertSelective(vip);
                if (i == 0) {
                    map.put("code", 400);
                    map.put("message", "提交失败");
                    map.put("result", "");
                } else if (i == 1) {
                    map.put("code", 200);
                    map.put("message", "ok");
                    map.put("result", "");
                }
            } else {
                map.put("code", 400);
                map.put("message", "提交失败");
                map.put("result", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            map.put("code", 400);
            map.put("message", "提交失败");
            map.put("result", "");
        }
        return map;
    }

    /**
     * 添加发票
     *
     * @param invoice Invoice
     * @return resp Map
     */
    @RequestMapping("/addinvoice")
    @ResponseBody
    public Map<String, Object> addInvoice(@RequestBody Invoice invoice) {
        Map<String, Object> resp = new HashMap<>();
        int code;
        String message;
        try {
            if (invoice.getUid() == null) {
                throw new RuntimeException("添加发票失败");
            } else {
                int i = invoiceService.insertSelective(invoice);
                if (i == 1) {
                    code = 200;
                    message = "ok";
                } else {
                    throw new RuntimeException("添加发票失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            code = 400;
            message = "添加发票失败";
        }
        resp.put("code", code);
        resp.put("message", message);
        return resp;
    }

    /**
     * 查询发票
     *
     * @param uid String
     * @return
     */
    @RequestMapping("/selectinvoice")
    @ResponseBody
    public Map<String, Object> selectInvoice(String uid) {
        Map<String, Object> map = new HashMap<>();
        List<Invoice> invoices = new ArrayList<>();
        try {
            invoices = invoiceService.selectAllByUid(Integer.valueOf(uid));
            if (invoices != null) {
                map.put("code", 200);
                map.put("message", "ok");
                map.put("result", invoices);
            } else {
                map.put("code", 400);
                map.put("message", "查询失败");
                map.put("result", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            map.put("code", 400);
            map.put("message", "查询失败");
            map.put("result", "");
        }
        return map;
    }

    /**
     * 删除发票
     *
     * @param iid
     * @return
     */
    @RequestMapping("/delinvoice")
    @ResponseBody
    public Map<String, Object> delInvoice(String iid) {
        Map<String, Object> model = new HashMap<>();
        try {
            int i = invoiceService.deleteByPrimaryKey(Integer.valueOf(iid));
            if (i == 0) {
                model.put("code", 400);
                model.put("message", "删除发票失败");
                model.put("result", "");
            } else if (i == 1) {
                model.put("code", 200);
                model.put("message", "ok");
                model.put("result", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            model.put("code", 400);
            model.put("message", "删除发票失败");
            model.put("result", "");
        }
        return model;
    }

    /**
     * 更新发票
     *
     * @param invoice Invoice
     * @return resp Map
     */
    @RequestMapping("/updateinvoice")
    @ResponseBody
    public Map<String, Object> updateInvoice(@RequestBody Invoice invoice) {
        Map<String, Object> resp = new HashMap<>();
        int code;
        String message;
        try {
            if (invoice.getIid() == null) {
                code = 60710;
                message = "未知的发票ID";
            } else {
                int i = invoiceService.updateByPrimaryKeySelective(invoice);
                if (i == 1) {
                    code = 200;
                    message = "ok";
                } else {
                    code = 60711;
                    message = "更新发票信息失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            code = 60712;
            message = "更新发票信息失败";
        }
        resp.put("code", code);
        resp.put("message", message);
        return resp;
    }

    /**
     * 查询用户推送消息
     *
     * @param uid
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("/selectMessage")
    @ResponseBody
    public Map<String, Object> SelectMessage(@RequestParam String uid, @RequestParam Integer rows, @RequestParam Integer page) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> resp = new HashMap<>();
        List<MessageVo> messages = new ArrayList<>();
        int code;
        String msg;
        if (rows == null) {
            rows = 10;
        }
        if (page == null) {
            page = 1;
        }
        Pagination pagination = new Pagination(rows, page);
        params.put("pagination", pagination);
        params.put("receiver", uid);
        try {
            messages = messageBatchService.listPageSelectForUser(params);
            code = 200;
            msg = "ok";
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
            code = 400;
            msg = "查询发生异常";
        }
        resp.put("code", code);
        resp.put("message", msg);
        resp.put("result", messages);
        return resp;
    }

    /**
     * 修改消息为已读
     *
     * @param mSgid String
     * @return Map
     */
    @RequestMapping("updateMsgStatus")
    @ResponseBody
    public Map<String, Object> UpdateMsgStatus(String mSgid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int i = 0;
        Message message = new Message();
        message.setMsgid(Integer.valueOf(mSgid));
        message.setIsread(1);
        try {
            i = messageService.updateByPrimaryKeySelective(message);
            if (i == 0) {
                map.put("code", 400);
                map.put("message", "修改状态失败，请重试");
                map.put("result", "");
            } else if (i == 1) {
                map.put("code", 200);
                map.put("message", "ok");
                map.put("result", "");
            }
        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "修改状态失败，请重试");
            map.put("result", "");

        }
        return map;

    }

    /**
     * 反馈建议
     *
     * @param suggest Suggestion
     * @return Map
     */
    @RequestMapping("/suggest")
    @ResponseBody
    public Map<String, Object> Suggest(@RequestBody Suggest suggest) {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        try {
            i = suggestService.insertSelective(suggest);
            if (i == 0) {
                map.put("code", 400);
                map.put("message", "发送建议失败");
                map.put("result", "");
            } else if (i == 1) {
                map.put("code", 200);
                map.put("message", "ok");
                map.put("result", "");
            }
        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
            map.put("code", 400);
            map.put("message", "发送建议失败");
            map.put("result", "");
        }
        return map;
    }




}
