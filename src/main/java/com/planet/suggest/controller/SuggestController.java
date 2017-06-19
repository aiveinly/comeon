package com.planet.suggest.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.suggest.domain.Suggest;
import com.planet.suggest.service.SuggestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yehao on 2016/2/23.
 */
@Controller
@RequestMapping("/suggest")
public class SuggestController {

    private Logger logger = LoggerFactory.getLogger(SuggestController.class);

    @Autowired
    private SuggestService suggestService;


    @RequestMapping("/index")
    public ModelAndView index() {

        return new ModelAndView("/suggest/suggestlist");
    }

    @RequestMapping("/listSuggest")
    @ResponseBody
    public Map listSuggest(HttpServletRequest request, int page, int rows) {

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Pagination pagination = null;

        List<Suggest> suggests = new ArrayList<>();


        try {

            pagination = new Pagination(rows, page);
            map.put("pagination", pagination);
            suggests = suggestService.listPageSelect(map);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        model.put("rows", suggests);
        model.put("total", pagination.getCount());

        return model;
    }


    @RequestMapping("/read")
    @ResponseBody
    public Map readSuggest(Integer sgid, Integer uid) {
        //init

        Map<String, Object> model = new HashMap<>();
        String msg = "修改失败";
        int success = 0;

        try {

            Suggest suggest = new Suggest();
            suggest.setSgid(sgid);
            suggest.setStatus(1);
            suggest.setUid(uid);

            success = suggestService.updateByPrimaryKeySelective(suggest);
            if (success == 1) {
                msg = "修改成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            msg = "修改失败";
            success = 0;
        }

        model.put("msg", msg);
        model.put("success", success);
        return model;
    }
}

