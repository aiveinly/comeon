package com.planet.wechat.service;

import com.planet.wechat.Utils.CheckModel;
import com.planet.wechat.Utils.EncoderHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by yehao on 2016/1/11.
 */
@Service
public class TokenServiceImpl implements TokenService {


    public String validate(String token, CheckModel tokenModel) {
        String signature = tokenModel.getSignature();
        Long timestamp = tokenModel.getTimestamp();
        Long nonce = tokenModel.getNonce();
        String echostr = tokenModel.getEchostr();
        if(signature != null && timestamp != null && nonce != null){
            String[] str = {token,"" + timestamp,"" + nonce};
            //字典排序
            Arrays.sort(str);
            String bigStr = str[0] + str[1] + str[2];
            String digest = EncoderHandler.encode("SHA1",bigStr).toLowerCase();

            if(digest.equals(signature)){
                System.out.println("验证成功");
                return echostr;
            }
        }
        throw new RuntimeException("Token验证失败");
    }
}
