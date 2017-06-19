package com.planet.pc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qingh on 2016/7/8 0008.
 */
@Controller
@RequestMapping("/pc")
public class PcPageController {

    @RequestMapping("/index")
    public String pcIndex() {
        return "/pc/index";
    }
}
