package com.planet.message.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.message.domain.Message;
import com.planet.message.service.MessageBatchService;
import com.planet.message.service.MessageService;
import com.planet.user.domain.UserAgent;
import com.planet.vo.MessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/3/3.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageBatchService messageBatchService;


    @RequestMapping("/index")
    public ModelAndView messageIndex() {
        return new ModelAndView("/message/index");
    }


    @RequestMapping("/listMessage")
    @ResponseBody
    public Map listMessage(HttpServletRequest request, int rows, int page) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        Pagination pagination = null;
        String receiver = null;
        String startTime = null;
        String endTime = null;

        List<MessageVo> messageVos = new ArrayList<>();
        try {

            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);

            startTime = request.getParameter("startTime");
            if (startTime != null && !"".equals(startTime)) {
                map.put("startTime", startTime);
            }

            endTime = request.getParameter("endTime");
            if (endTime != null && !"".equals(endTime)) {
                map.put("endTime", endTime);
            }

            receiver = request.getParameter("receiver");
            if (receiver != null && !"".equals(receiver)) {
                map.put("name", receiver);
            }

            messageVos = messageBatchService.listPageSelectMessage(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.put("rows", messageVos);
        model.put("total", pagination.getCount());
        return model;
    }


    @RequestMapping("/removeMessage")
    @ResponseBody
    public Map removePreOrder(int msgid) {
        //init
        Map<String, Object> model = new HashMap<>();
        String msg = "删除失败";
        int success = 0;
        try {
            success = messageBatchService.deleteByPrimaryKey(msgid);
            if (success == 1) {
                msg = "删除成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "删除失败";
            success = 0;
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/sendMessage")
    @ResponseBody
    public Map sendMessage(String uid, String content) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        String msg = "发送失败";
        int success = 0;
        try {

            map.put("receiver", uid);
            map.put("message", content);
            map.put("mType", 1);
            map.put("sender", 1);
            if (uid == "0") {
                success = messageBatchService.insertMessageAll(map);
            } else {
                success = messageBatchService.insertMessage(map);
            }

            if (success == 1) {
                msg = "发送成功";
            }
            logger.info("插入消息成功");
        } catch (Exception e) {
            logger.error("提交消息错误", e);
            msg = "发送失败";
            success = 0;
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


}
