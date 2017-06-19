package com.planet.common.sms;

/**
 * 类SmsService.java的实现描述：TODO 发送短信
 * 
 * @author yehao 2015年11月19日 下午4:27:54
 */
public interface SmsService {

    /**
     * 发送短信
     * 
     * @param content
     * @param toPhone
     * @return
     */
    public String sendSms(String content, String toPhone);
}
