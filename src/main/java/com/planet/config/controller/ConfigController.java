package com.planet.config.controller;

import com.planet.config.domain.Config;
import com.planet.config.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Qinghua on 2016-03-21 11:19.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {
    private static final Logger logger =
            LoggerFactory.getLogger(ConfigController.class);
    @Autowired
    ConfigService configService;
    @ResponseBody
    @RequestMapping("/get-config")
    public Map<String,Object> getSysConfig(Integer configId){
        Map<String, Object> resp = new HashMap<>();
        String msg;
        int code;
        try {
            Config config = configService.selectByPrimaryKey(configId);
            resp.put("result",config);
            code = 200;
            msg = "ok";
        } catch (Exception e) {
            logger.error("GetSysConfig Error:",e);
            code = 60012;
            msg = "获取配置信息失败";
        }
        resp.put("code", code);
        resp.put("message", msg);
        return resp;
    };
}
