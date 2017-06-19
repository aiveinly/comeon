package com.planet.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * app的控制器
 * Created by Qinghua on 2016-01-31 10:40.
 */
@Controller
@RequestMapping("/app")
public class AppController {
    @RequestMapping("/index")
    public ModelAndView appView() {
        Map<String, Object> data = new HashMap<>();
        return new ModelAndView("/wechat/index",data);
    }
}
