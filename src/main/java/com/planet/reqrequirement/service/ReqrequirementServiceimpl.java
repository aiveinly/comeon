package com.planet.reqrequirement.service;

import com.planet.idrule.dao.IdRuleMapper;
import com.planet.idrule.service.IdRuleService;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordorder.service.OrdOrderService;
import com.planet.reqrequirement.dao.ReqRequirementMapper;
import com.planet.reqrequirement.domain.ReqRequirement;
import com.planet.sysfile.domain.SysFile;
import com.planet.sysfile.service.SysFileService;
import com.planet.vo.ReqrequirementVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/21.
 */
@Service
@Transactional
public class ReqrequirementServiceimpl implements ReqrequirementService {

    private static final Logger logger = LoggerFactory.getLogger(ReqrequirementServiceimpl.class);

    @Autowired
    private ReqRequirementMapper reqRequirementMapper;

    @Autowired
    private IdRuleService idRuleService;

    @Autowired
    private OrdOrderService ordOrderService;

    @Autowired
    private SysFileService sysFileService;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String rid)throws  Exception {
        int i = 0 ;
        i = reqRequirementMapper.deleteByPrimaryKey(rid);
        if(i==0){
         logger.info("删除需求失败");
        }else if (i==1){
            logger.info("删除需求成功");
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(ReqRequirement reqRequirement) throws  Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        reqRequirement.setCreatedate(date);
        reqRequirement.setStatus(0);
        if ((reqRequirement.getUid()==null)||("".equals(reqRequirement.getUid()))||("null".equals(reqRequirement.getUid()))){
            i=0;
        } else
        {
            i = reqRequirementMapper.insert(reqRequirement);
        }

        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
            OrdOrder ordOrder = new OrdOrder();
            ordOrder.setRid(reqRequirement.getRid());
            ordOrder.setUid(String.valueOf(reqRequirement.getUid()));
            ordOrder.setStatus(0);         // 订单已提交     modify  by  asta  2016/3/25
            ordOrderService.insertSelective(ordOrder);
            logger.info("开始生成订单。。。");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(ReqRequirement reqRequirement) throws  Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        reqRequirement.setCreatedate(date);
        reqRequirement.setRid(idRuleService.selectByNumName("R"));
        reqRequirement.setStatus(0);
        if ((reqRequirement.getUid()==null)||("".equals(reqRequirement.getUid()))||("null".equals(reqRequirement.getUid()))){
            i=0;
        } else
        {
            i = reqRequirementMapper.insertSelective(reqRequirement);
        }

        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ReqRequirement selectByPrimaryKey(String rid) throws  Exception {
        return reqRequirementMapper.selectByPrimaryKey(rid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ReqRequirement reqRequirement) throws  Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        reqRequirement.setUpdatedate(date);
        i = reqRequirementMapper.updateByPrimaryKeySelective(reqRequirement);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(ReqRequirement reqRequirement) throws  Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        reqRequirement.setUpdatedate(date);
        i = reqRequirementMapper.updateByPrimaryKey(reqRequirement);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ReqRequirement> listPagereqRequirements(Map<String, Object> map) throws  Exception {
        List<ReqRequirement> reqRequirements = new ArrayList<>();
        reqRequirements = reqRequirementMapper.listPagereqRequirements(map);
        return reqRequirements;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ReqrequirementVo selectByRid(String Rid) throws Exception {
        ReqrequirementVo reqrequirementVo = new ReqrequirementVo();
        ReqRequirement reqRequirement =null;
        int Fileid = 0;
        List<SysFile> sysFiles = new ArrayList<>();
        reqRequirement = reqRequirementMapper.selectByPrimaryKey(Rid);

        if(reqRequirement.getFileid()==null && "".equals(reqRequirement.getFileid())){
            logger.info("没有文件。。。");
        }else {
            sysFiles = sysFileService.selectByFileId(reqRequirement.getFileid());
        }
        reqrequirementVo.setReqRequirement(reqRequirement);
        reqrequirementVo.setSysFiles(sysFiles);
        return reqrequirementVo;
    }
}
