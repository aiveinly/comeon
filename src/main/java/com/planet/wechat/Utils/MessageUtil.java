package com.planet.wechat.Utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/1/11.
 */
public class MessageUtil {

    // 消息类型
    public static final String MESSAGE_TEXT = "text";

    // 事件类型
    public static final String MESSAGE_EVENT = "event";

    // 关注
    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    // 取消关注
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_CLICK = "CLICK";

    public static final String MESSAGE_VIEW = "VIEW";


    /**
     * parse xml as Map
     * @param inputStream
     * @return
     */
    public static Map<String, String> xmlToMap(InputStream inputStream) {

        SAXReader reader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        try {
            Document document = reader.read(inputStream);
            Element element = document.getRootElement();
            List<Element> list = element.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * parse object as xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {

        XStream stream = new XStream();
        /** 转换的xml的标签会是这个对象的类名，如果要转成xml的话用这个方法 */
        stream.alias("xml", textMessage.getClass());
        return stream.toXML(textMessage);
    }
}
