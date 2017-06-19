package com.planet.common.sms;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类SmsServiceImpl.java的实现描述：TODO 发送短信服务实现
 *
 * @author yehao 2015年11月19日 下午5:20:35
 */
@Service
public class SmsServiceImpl implements SmsService {

    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    // 智能匹配模版发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    // 发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    /**
     * 发送短信
     */
    @Override
    public String sendSms(String content, String toPhone) {
        /*
        // apikey
        String apikey = "dc1579f1c0d69634115334a906b8e602";
        // 目标手机号
        String phone = toPhone;

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", content);
        params.put("mobile", phone);
        return post(URI_SEND_SMS, params);*/

        String url = "http://222.73.117.156/msg/";// 应用地址
        String account = "kunma888";// 账号
        String pswd = "Comeon365";// 密码
        String mobile = toPhone;// 手机号码，多个号码使用","分割
//        String msg = "亲爱的用户，您的验证码是123456，5分钟内有效。";// 短信内容
        String msg = content;
        boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
        String product = null;// 产品ID
        String extno = null;// 扩展码
        String returnString = null;
        try {
            returnString = HttpSender.batchSend(url, account, pswd, mobile, msg, needstatus, product, extno);
            // TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }

        return returnString;
    }



    /**
     * 发送post请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }


    public static void main(String[] args) {

        SmsServiceImpl smsService = new SmsServiceImpl();
        smsService.sendSms("亲爱的用户，您的验证码是123456，5分钟内有效。","xxxxxxxxxxxxxxxxx");
    }

}
