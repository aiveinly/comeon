package com.planet.proproduct.service;

import com.planet.idrule.service.IdRuleService;
import com.planet.prodict.dao.ProDictMapper;
import com.planet.proproduct.dao.ProProductMapper;
import com.planet.proproduct.domain.ProProduct;
import com.planet.proproductsale.service.ProProductSaleService;
import com.planet.utils.FileOperateUtil;
import com.planet.vo.ProductAllVo;
import com.planet.vo.ProductDetailVo;
import com.planet.vo.ProductListBgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * Created by Li on 2016/1/21.
 */
@Service
@Transactional
public class ProProductServiceimpl implements ProProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProProductServiceimpl.class);

    @Autowired
    private ProProductMapper proProductMapper;

    @Autowired
    private ProDictMapper proDictMapper;

    @Autowired
    private IdRuleService idRuleService;

    @Autowired
    private ProProductSaleService proProductSaleService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String pid) throws Exception {
        int i = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("pid",pid);
        int j =  proProductSaleService.selectByPid(map);
        if(j==0){
            i = proProductMapper.deleteByPrimaryKey(pid);
            if (i == 0) {
                logger.info("删除产品信息失败");
            } else if (i == 1) {
                logger.info("删除产品信息成功");
            }
        }else if(j>0){
            i=0;
            logger.info("删除产品信息失败");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(ProProduct proProduct) throws Exception {
        int i = 0;
        i = proProductMapper.insert(proProduct);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    /**
     * 添加产品上传图片
     *
     * @param proProduct
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(ProProduct proProduct, HttpServletRequest request) throws Exception {
        int i = 0;
        int j = 0;
        List list = new ArrayList();
        MultipartHttpServletRequest mRequest = null;
        list = FileOperateUtil.upload(request);

        mRequest = (MultipartHttpServletRequest) request;
        Iterator<Map.Entry<String, MultipartFile>> it = mRequest.getFileMap().entrySet().iterator();

        // by asta  多文件上传 判断文本ID

        if (list.size() > 0) {
            while (it.hasNext()) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                if (mFile.getName().equals("file")) {
                    proProduct.setImgurl(list.get(j).toString());
                }
                if (mFile.getName().equals("file2")) {
                    proProduct.setImgurl2(list.get(j).toString());
                }
                j=j+1;
            }

        }
        String pid = idRuleService.selectByNumName("P");
        proProduct.setPid(pid);
        i = proProductMapper.insertSelective(proProduct);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(ProProduct proProduct) throws Exception {
        int i = 0;
        String pid = idRuleService.selectByNumName("P");
        proProduct.setPid(pid);
        i = proProductMapper.insertSelective(proProduct);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;

    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProProduct selectByPrimaryKey(String pid) throws Exception {
        return proProductMapper.selectByPrimaryKey(pid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ProProduct proProduct) throws Exception {
        int i = 0;
        i = proProductMapper.updateByPrimaryKeySelective(proProduct);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ProProduct proProduct, HttpServletRequest request) throws Exception {
        int i = 0;
        int j = 0;
        List list = new ArrayList();
        MultipartHttpServletRequest mRequest = null;
        list = FileOperateUtil.upload(request);
        mRequest = (MultipartHttpServletRequest) request;
        Iterator<Map.Entry<String, MultipartFile>> it = mRequest.getFileMap().entrySet().iterator();

        // by asta  多文件上传 判断文本ID
        if (list.size() > 0) {
            while (it.hasNext()) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                if (mFile.getName().equals("file")) {
                    proProduct.setImgurl(list.get(j).toString());
                }
                if (mFile.getName().equals("file2")) {
                    proProduct.setImgurl2(list.get(j).toString());
                }
                j=j+1;
            }
        }

        //获得产品图片和推荐图片的标记,  标记作用是 是否清空图片
        String productImg_ = request.getParameter("productImg_");
        String recommandImg_ = request.getParameter("recommandImg_");
        if("1".equals(productImg_)){
            proProduct.setImgurl("null");
        }
        if("1".equals(recommandImg_)){
            proProduct.setImgurl2("null");
        }
        i = proProductMapper.updateByPrimaryKeySelective(proProduct);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(ProProduct proProduct) throws Exception {
        int i = 0;
        i = proProductMapper.updateByPrimaryKey(proProduct);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProductListBgVo> listPageSelectProProduct(Map map) throws Exception {
        //查询list
        return proProductMapper.listPageSelectProProduct(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProductAllVo> listPageselectProductSaleList(Map map) throws Exception {
        return proProductMapper.listPageselectProductSaleList(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProductDetailVo> selectProductDetail(Map map) throws Exception {
        return proProductMapper.selectProductDetail(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int selectById(Map<String, Object> map) throws Exception {
        return proProductMapper.selectById(map);
    }





}
