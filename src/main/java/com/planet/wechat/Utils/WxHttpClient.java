package com.planet.wechat.Utils;

import com.alibaba.fastjson.JSONObject;
import com.planet.vo.Token;
import com.planet.vo.WeixinUserInfo;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

@Service("wxHttpClient")
public class WxHttpClient {
    public static final Logger logger = Logger.getLogger(WxHttpClient.class);

    private static final String WX_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String WPCharset = "UTF-8";
    private static final String JsonContentType = "application/json";
    private static final String XmlContentType = "application/xml";

    /**
     * post 请求回调信息
     *
     * @param url
     * @param data
     * @return
     */
    public String post(String url, String data) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(DEFAULT_TIMEOUT)
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity postEntity = new StringEntity(data,
                    ContentType.create(JsonContentType, WPCharset));
            httpPost.setEntity(postEntity);
            try {
                response = httpClient.execute(httpPost);
            } catch (Exception e) {
                if (e instanceof UnknownHostException) {
                    try {
                        response = httpClient.execute(httpPost);
                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }
                }
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        if (logger.isDebugEnabled()) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (response == null) {
                logger.error("http request fail, url = " + url);
                return "";
            }

            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("http request fail");
            }
            byte[] result = EntityUtils.toByteArray(response.getEntity());
            return new String(result, WPCharset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }


    public String postXML(String url, String data) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(DEFAULT_TIMEOUT)
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity postEntity = new StringEntity(data,
                    ContentType.create(XmlContentType, WPCharset));
            httpPost.setEntity(postEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Access failed");
            }
            byte[] result = EntityUtils.toByteArray(response.getEntity());
            return new String(result, WPCharset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    /**
     * 获取用户openId
     *
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    public String getOpenId(String code, String state, HttpSession httpSession) throws Exception {
        String url = WX_URL + "&appid=" + WeChatConf.APPID + "&secret=" + WeChatConf.APPSECRET
                + "&code=" + code;
        String openid = null;
        String response = post(url, "");
        JSONObject json = JSONObject.parseObject(response);
        if (json != null && json.containsKey("openid")) {
            openid = json.getString("openid");
        }
        Token accessToken = new Token();
        WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
        Token token = (Token) httpSession.getAttribute("accessToken");
        weixinUserInfo = getUserInfo(token.getAccessToken(), json.getString("openid"));
        return openid;
    }

    public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        net.sf.json.JSONObject jsonObject = WechatCommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                logger.error("获取用户信息发生异常",e);
                if (weixinUserInfo != null) {
                    if (0 == weixinUserInfo.getSubscribe()) {
                        logger.error("用户{}已取消关注");
                    } else {
                        int errorCode = jsonObject.getInt("errcode");
                        String errorMsg = jsonObject.getString("errmsg");
                        logger.error("获取用户信息失败 errcode:{} errmsg:{}" + errorCode + "==" + errorMsg);
                    }
                }
            }
        }
        return weixinUserInfo;
    }


    public static String getPrepayId(RequestHandler reqHandler) {
        String requestUrl = null;
        String prepay_id = null;
        try {
            requestUrl = reqHandler.getRequestURL();
            logger.info("requestUrl===================" + requestUrl);
            //统一下单接口提交  xml格式
            URL orderUrl = new URL(WeChatConf.unifiedorder);
            HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
            conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
            conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setUseCaches(false); // Post 请求不能使用缓存
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
            conn.setRequestProperty("Content-Length", requestUrl.length() + "");
            String encode = "utf-8";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);
            out.write(requestUrl.toString());
            out.flush();
            out.close();
            String result = getOut(conn);
            logger.info("result=========返回的xml=============" + result);
            Map<String, String> orderMap = XMLUtil.doXMLParse(result);
            logger.info("orderMap===========================" + orderMap);
            //得到的预支付id
            prepay_id = orderMap.get("prepay_id");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return prepay_id;
    }

    /**
     * 获取xml输出
     *
     * @param conn
     * @return
     * @throws IOException
     */
    public static String getOut(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        // 获取响应内容体
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        String line = "";
        StringBuffer strBuf = new StringBuffer();
        while ((line = in.readLine()) != null) {
            strBuf.append(line).append("\n");
        }
        in.close();
        return strBuf.toString().trim();
    }


}