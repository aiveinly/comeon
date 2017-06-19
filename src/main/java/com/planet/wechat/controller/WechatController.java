package com.planet.wechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.planet.utils.HttpTookit;
import com.planet.utils.SpringTask;
import com.planet.vo.Token;
import com.planet.wechat.Utils.*;
import com.planet.wechat.service.TokenService;
import org.codehaus.jackson.map.annotate.JsonView;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yehao on 2016/1/11.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    private Logger log = org.slf4j.LoggerFactory.getLogger(WechatController.class);

    //appid
    private static final String APPID = "wx981784dca1f133d1";
    //appsecret
    private static final String APPSECRET = "14b5a074a16922654816a71dbd6ebe3b";
    //wechat token
    private static final String TOKEN = "rongan";

    // 创建公众号菜单
    public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    //url for get accesstoken
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public void check(HttpServletResponse response, CheckModel tokenModel) {
        //init
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            //validate token
            pw.write(tokenService.validate(TOKEN, tokenModel));
            pw.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        } finally {
            pw.close();
        }
    }


    @RequestMapping(value = "/call", method = RequestMethod.POST)
    public void wechatHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            // set Charset
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            //get a inputstream from request
            InputStream is = request.getInputStream();
            //paser xml as map
            Map<String, String> map = MessageUtil.xmlToMap(is);

            //get message element
            String eventKey = map.get("EventKey");
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String createTime = map.get("CreateTime");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String msgId = map.get("MsgId");

            //reply
            TextMessage textMessage = new TextMessage();
            /** 回复的时候 ， 发送人和 接收人 互换 */
            textMessage.setFromUserName(toUserName);
            textMessage.setToUserName(fromUserName);
            /** 消息时间设置当前时间 */
            textMessage.setCreateTime("" + new Date().getTime());
            /** 回复文本消息 */
            textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
            textMessage.setContent("hello world");

            //parse object as xml
            String message = MessageUtil.textMessageToXml(textMessage);

            /** 从response里获得一个输出流，然后输出xml消息 */
            response.getOutputStream().write(message.trim().getBytes("UTF-8"));
            System.out.println(message.trim());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * create button memu
     *
     * @return
     */
    @RequestMapping("/createButtonMenu")
    @ResponseBody
    public Object createButtonMenu() {


        JSONObject button = new JSONObject();

        JSONArray buttonArray = new JSONArray();
//----------------------------------------------------------
        JSONObject button1 = new JSONObject();
        button1.put("type", "view");
        button1.put("name", "菜单1");
        button1.put("url", "http://www.baidu.com");
        buttonArray.add(button1);


        JSONObject button2 = new JSONObject();
        button2.put("type", "view");
        button2.put("name", "菜单2");
        button2.put("url", "http://www.baidu.com");
        buttonArray.add(button2);


        JSONArray subButtonArray = new JSONArray();
        JSONObject subButton1 = new JSONObject();
        subButton1.put("type", "view");
        subButton1.put("name", "子菜单1");
        subButton1.put("url", "http://www.baidu.com");

        JSONObject subButton2 = new JSONObject();
        subButton2.put("type", "view");
        subButton2.put("name", "子菜单2");
        subButton2.put("url", "http://www.baidu.com");

        subButtonArray.add(subButton1);
        subButtonArray.add(subButton2);

        JSONObject button3 = new JSONObject();
        button3.put("name", "菜单3");
        button3.put("sub_button", subButtonArray);

        buttonArray.add(button3);
        button.put("button", buttonArray);


        return HttpTookit.doPostByJsonParam(MENU_CREATE_URL + getAccessToken(), button);

    }

    /**
     * get accessToken
     * @return
     */
    public static String getAccessToken() {
        JSONObject json = JSONObject.parseObject(HttpTookit.doGet(ACCESS_TOKEN_URL, null));

        Object accessToken = json.get("access_token");
        if (accessToken != null) {
            return String.valueOf(accessToken);
        } else {
            throw new RuntimeException("获得AccessToken失败");
        }
    }


    @RequestMapping("/signature")
    @ResponseBody
    public Map<String,Object> payIndex(HttpSession httpSession, String url) {
        Map<String,Object> map = new HashMap<>();
        Token token = (Token) httpSession.getAttribute("accessToken");
        if(token!=null&&!"".equals(token)) {
        String ticket = (String)  httpSession.getAttribute("jsapi_ticket");
        String appid = WeChatConf.APPID;
        String noncestr = WeChatUtils.getNonceStr();//生成随机字符串
        String timestamp = WeChatUtils.getTimeStamp();//生成1970年到现在的秒数.
        String signature = WeChatUtils.getSignature(ticket,noncestr,timestamp,url);
        Map<String, Object> obj = new HashMap<>();
        obj.put("appid", appid);
        obj.put("timestamp", timestamp);
        obj.put("noncestr", noncestr);
        obj.put("signature", signature);
        map.put("result",obj);
        map.put("msg","ok");
        map.put("code",200);

        log.info("jsapi_ticket"+ticket);
        log.info("appid:"+appid);
        log.info("timestamp:"+timestamp);
        log.info("noncestr:"+noncestr);
        log.info("signature:"+signature);
        }else{
            //如果为空重新获取
            SpringTask springTask = new SpringTask();
            springTask.getToken(httpSession);
            String ticket = (String)  httpSession.getAttribute("jsapi_ticket");
            String appid = WeChatConf.APPID;
            String noncestr = WeChatUtils.getNonceStr();//生成随机字符串
            String timestamp = WeChatUtils.getTimeStamp();//生成1970年到现在的秒数.
            String signature = WeChatUtils.getSignature(ticket,noncestr,timestamp,url);
            Map<String, Object> obj = new HashMap<>();
            obj.put("appid", appid);
            obj.put("timestamp", timestamp);
            obj.put("noncestr", noncestr);
            obj.put("signature", signature);
            map.put("result",obj);
            map.put("msg","ok");
            map.put("code",200);

            log.info("jsapi_ticket"+ticket);
            log.info("appid:"+appid);
            log.info("timestamp:"+timestamp);
            log.info("noncestr:"+noncestr);
            log.info("signature:"+signature);
        }

        //return new ModelAndView("/pay/pay", obj);
        return map;
    }

}
