package com.planet.test.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.message.service.MessageBatchService;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.ordpreorder.service.OrdPreOrderService;
import com.planet.quoquotation.service.QuoQuotationService;
import com.planet.suggest.domain.Suggest;
import com.planet.suggest.service.SuggestService;
import com.planet.test.domain.Account;
import com.planet.test.service.TestService;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.user.service.UserServiceimpl;
import com.planet.vo.MessageVo;
import com.planet.vo.OrdOrderVo;
import com.planet.vo.QuoQuotationVo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by yehao on 2016/1/8.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuoQuotationService quoQuotationService;

    @Autowired
    private OrdOrderService  ordOrderService;

    @Autowired
    private SuggestService suggestService;

    @Autowired
    private MessageBatchService messageBatchService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceimpl.class);

    @RequestMapping("/testProject")
    @ResponseBody
    public String testProject() {
        return String.valueOf(testService.addId());

    }


    @RequestMapping("/testPage")
    @ResponseBody
    public List<Integer> testPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagination", new Pagination(3, 1));
        return testService.searchAllId(map);
    }


    @RequestMapping("/testEasyUIPage")
    public ModelAndView toTestPage() {
        return new ModelAndView("/test/TestJquery");

    }

    @RequestMapping("/testEasyUI")
    @ResponseBody
    public Map testEasyUI(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();

        List<Account> accounts = new ArrayList<Account>();

        for (int i = 0; i < 10; i++) {
            accounts.add(new Account(i, "aaa", "bbb"));
        }


        model.put("total", accounts.size());
        model.put("rows", accounts);

        return model;
    }


    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/menu/menu");

    }

    @RequestMapping("/addUser")
    public String addUer() throws Exception {
        Date date = new Date();
        UserAgent userAgent = new UserAgent();
        userAgent.setIid(000000001);
        userAgent.setQid("000000001");
        userAgent.setUsername("zhangsan");
        userAgent.setName("张三");
        userAgent.setPassword("123456");
        userAgent.setVip(0);
        userAgent.setOpenid("ov5IJvwpTx4ZAqOZ_a_OyD6XJFis");
        userAgent.setPoint(0);
        userAgent.setTel("18772955633");
        userAgent.setLogindate(date);
        userService.insert(userAgent);

        return null;
    }

    @RequestMapping("/selectUser")
    @ResponseBody
    public Map<String,Object> selectUser() throws Exception{
        Map<String,Object> map = new HashMap<>();
        QuoQuotationVo quoQuotationVo = new QuoQuotationVo();
        quoQuotationVo = quoQuotationService.insertSelectQuo("1","1");
        map.put("aa",quoQuotationVo);
        return map;

    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update() throws Exception{
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> model = new HashMap<>();
        Pagination pagination = null;
        List<Suggest> result = new ArrayList();
        pagination = new Pagination(Integer.valueOf(10), Integer.valueOf(1));
        map.put("pagination", pagination);
        result = suggestService.listPageSelect(map);
        model.put("result",result);
        return model;


    }

    @RequestMapping("/listPageselect")
    @ResponseBody
    public  Map<String,Object> listPageselect() throws Exception{
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> model = new HashMap<>();
        Pagination pagination = null;
        List<OrdOrderVo> result = new ArrayList();
        pagination = new Pagination(Integer.valueOf(1), Integer.valueOf(10));
        map.put("pagination", pagination);
        result = ordOrderService.listPageSelectOrder(map);
        model.put("result",result);
        return model;
    }

    @RequestMapping("sendMessageAll")
    @ResponseBody
    public  Map<String,Object> SendMessageAll(){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> model = new HashMap<>();
        try {
            map.put("message",UUID.randomUUID());
            map.put("mType","0");
            //系统发送
            map.put("sender",0);
            messageBatchService.insertMessageAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping("/selectMessage")
    @ResponseBody
    public Map<String,Object> SelectMessage(){
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination(10,1);
        List<MessageVo> messageVos = new ArrayList<>();
        try {
            map.put("pagination",pagination);
            messageVos =  messageBatchService.listPageSelectMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result",messageVos);
        return map;
    }

}
