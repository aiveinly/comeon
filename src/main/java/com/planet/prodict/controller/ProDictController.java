package com.planet.prodict.controller;

import com.planet.common.mybatis.plugins.page.Pagination;
import com.planet.prodict.domain.ProDict;
import com.planet.prodict.service.ProDictService;
import com.planet.vo.ProDictVo;
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
 * Created by Li on 2016/1/25.
 */
@Controller
@RequestMapping("/dict")
public class ProDictController {

    private static final Logger logger = LoggerFactory.getLogger(ProDictController.class);

    @Autowired
    private ProDictService proDictService;

    @RequestMapping("/dictlist")
    @ResponseBody
    public Map<String, Object> DictList(
            @RequestParam(value = "type", required = true) final String type,
            @RequestParam(value = "pre", required = false) final String pre,
            @RequestParam(value = "page", required = true) final Integer page,
            @RequestParam(value = "rows", required = true) final Integer rows,
            @RequestParam(value = "parentId", required = false) final Integer parentId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        Pagination pagination = new Pagination(page, rows);
        param.put("pagination", pagination);
        param.put("type", Integer.valueOf(type));
        param.put("pre", pre);
        param.put("parentId", parentId);
        List<ProDictVo> proDicts = proDictService.listPageProDict(param);
        logger.info(proDicts.toString());
        model.put("code", 200);
        model.put("message", "ok");
        model.put("result", proDicts);
        return model;
    }


    @RequestMapping("/toProdict")
    public ModelAndView index(HttpServletRequest request) {
        //init
        Map<String, Object> model = new HashMap<String, Object>();
        String type = request.getParameter("type");
        model.put("type", type);
        return new ModelAndView("/dictlist/prodict", model);
    }

    /**
     * get dic List
     *
     * @return
     */
    @RequestMapping("/proDictList")
    @ResponseBody
    public Map getUserList(HttpServletRequest request, int page, int rows) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        String type = null;
        String dicname = null;
        List<ProDictVo> proDicts = null;
        Pagination pagination = null;

        try {
            pagination = new Pagination(rows, page);
            type = request.getParameter("type");
            dicname = request.getParameter("dicname");
            if (type == null) {
                throw new RuntimeException("类型错误");
            }
            map.put("pagination", pagination);
            map.put("type", type);
            map.put("dictname", dicname);
            proDicts = proDictService.listPageProDict(map);

        } catch (Exception e) {
            logger.error(e.getMessage());
            proDicts = null;
        }
        model.put("rows", proDicts);
        model.put("total", pagination.getCount());
        return model;
    }

    @RequestMapping("/editDic")
    @ResponseBody
    public Map editDic(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> model = new HashMap<String, Object>();
        int success = 0;
        String msg = "修改失败";
        String dicname = null;
        String pre = null;
        String sn = null;
        String did = null;

        ProDict proDict = new ProDict();
        try {
            did = request.getParameter("did");
            pre = request.getParameter("pre");
            sn = request.getParameter("sn");
            dicname = request.getParameter("dicname");
            proDict.setDid(Integer.parseInt(did));
            proDict.setPre(pre);
            proDict.setSn(Integer.parseInt(sn));
            proDict.setDictname(dicname);
            success = proDictService.updateByPrimaryKeySelective(proDict, request);
            if (success == 1) {
                msg = "修改成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "修改失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/deleteDic")
    @ResponseBody
    public Map deleteDic(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        int success = 0;
        String msg = "删除失败";
        String did = null;
        try {
            did = request.getParameter("did");
            success = proDictService.deleteByPrimaryKey(Integer.parseInt(did));
            if (success == 1) {
                msg = "删除成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "删除失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }


    @RequestMapping("/addDic")
    @ResponseBody
    public Map addDic(HttpServletRequest request) {
        //init
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        String msg = "添加失败";
        int success = 0;
        String dicname = null;
        String pre = null;
        String sn = null;
        String type = null;
        String parentid = null;
        ProDict proDict = new ProDict();
        try {
            pre = request.getParameter("pre");
            sn = request.getParameter("sn");
            dicname = request.getParameter("dicname");
            type = request.getParameter("type");

            if ("1".equals(type)) {
                parentid = "0";
            } else {
                parentid = request.getParameter("parentId");
            }

            proDict.setPre(pre.toUpperCase());
            proDict.setSn(Integer.parseInt(sn));
            proDict.setDictname(dicname);
            proDict.setType(Integer.parseInt(type));
            proDict.setParentid(Integer.parseInt(parentid));
            success = proDictService.insertSelective(proDict, request);
            if (success == 1) {
                msg = "添加成功";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = 0;
            msg = "添加失败";
        }
        model.put("msg", msg);
        model.put("success", success);
        return model;
    }

    @RequestMapping("/pre")
    @ResponseBody
    public Map<String, Object> PreInfo(int type) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        List<ProDict> proDicts = new ArrayList<>();
        map.put("type", type);
        proDicts = proDictService.selectPreInfo(map);
        model.put("code", 200);
        model.put("success", "ok");
        model.put("result", proDicts);
        return model;
    }


    /**
     * put data for combobox
     *
     * @return
     */
    @RequestMapping("/listDidAndDicName")
    @ResponseBody
    public List getDidAndDictName(Integer type, @RequestParam(required = false) Integer parentId) {

        Map<String, Object> map = new HashMap<>();

        List<ProDict> data = null;
        try {
            map.put("type", type);
            if (parentId != null)
                map.put("parentid", parentId);
            data = proDictService.selectProall(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
