package com.planet.utils;

import com.alibaba.fastjson.JSON;
import com.planet.vo.Token;
import com.planet.wechat.Utils.HttpClientUtil;
import com.planet.wechat.Utils.WeChatConf;
import com.planet.wechat.Utils.WechatCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by lijunhua on 16/5/18.
 */
public class SpringTask {
    private static Logger logger = LoggerFactory.getLogger(SpringTask.class);



    /**
     * 定时获取access_token
     */
    public void getToken(HttpSession httpSession) {
        Token accessToken = WechatCommonUtil.getToken("wx981784dca1f133d1", "14b5a074a16922654816a71dbd6ebe3b");
        if (null != accessToken) {
            logger.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
            httpSession.setAttribute("accessToken", accessToken);
            //生成jsapi_ticket进行缓存
            String access_token = accessToken.getAccessToken();
            String ticketParam = "access_token=" + access_token + "&type=jsapi";
            String ticketJsonStr = HttpClientUtil.SendGET(WeChatConf.jsapi_ticket, ticketParam);
            Map ticketMap = JSON.parseObject(ticketJsonStr);
            String ticket = (String) ticketMap.get("ticket");
            httpSession.setAttribute("jsapi_ticket", ticket);
        }

    }
}
