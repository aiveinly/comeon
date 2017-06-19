package com.planet.idrule.service;

import com.planet.idrule.dao.IdRuleMapper;
import com.planet.idrule.domain.IdRule;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Li on 2016/1/31.
 */
@Service
@Transactional
public class IdRuleServiceimpl implements IdRuleService {

    private static final Logger logger = LoggerFactory.getLogger(IdRuleServiceimpl.class);

    @Autowired
    private IdRuleMapper idRuleMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        int i = 0;
        i = idRuleMapper.deleteByPrimaryKey(id);
        if(i==0){
            logger.info("删除规则id失败");
        }else if(i==1){
            logger.info("删除规则id成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(IdRule idRule) throws Exception {
        int i = 0;
        i = idRuleMapper.insert(idRule);
        if(i==0){
            logger.info("插入规则id失败");
        }else{
            logger.info("插入规则id成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(IdRule idRule) throws Exception {
        int i = 0;
        i = idRuleMapper.insertSelective(idRule);
        if(i==0){
            logger.info("插入规则id失败");
        }else if(i==1){
            logger.info("插入规则id成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IdRule selectByPrimaryKey(Integer id) throws Exception {
        return idRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(IdRule idRule) throws Exception {
        int i = 0;
        i = idRuleMapper.updateByPrimaryKeySelective(idRule);
        if(i==0){
            logger.info("更新规则id失败");
        }else if(i==1){
            logger.info("更新规则id成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(IdRule idRule) throws Exception {
        int i = 0;
        i = idRuleMapper.updateByPrimaryKey(idRule);
        if(i==0){
            logger.info("更新规则id失败");
        }else if(i==1){
            logger.info("更新规则id成功");
        }
        return i;
    }

    /**
     * 生成对应规则编号
     * @param numName
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String selectByNumName(@Param(value = "numName") String numName)throws Exception {
        IdRule idRule = new IdRule();
        Date date = new Date();
        String CurNum=null;
        int date1 = Integer.valueOf(new SimpleDateFormat("yyMM").format(date));//当前日期
        idRule= idRuleMapper.selectByNumName(numName);
        int date2 =Integer.valueOf(idRule.getLastNum().substring(0,4));//上次生成日期
        if (date1<date2){
            return null;
        }else if(date1==date2){
            DecimalFormat df=new DecimalFormat(GenerateDigit(idRule.getNumCount()));
            CurNum = df.format(idRule.getCurNum()+1);
            idRule.setCurNum(idRule.getCurNum()+1);
            idRule.setCurNum(Integer.valueOf(CurNum));
            idRule.setLastNum(date1+CurNum);
        }else if(date1>date2){
            idRule.setCurNum(1);
            DecimalFormat df=new DecimalFormat(GenerateDigit(idRule.getNumCount()));
            CurNum = df.format(1);
            idRule.setLastNum(date1+CurNum);
            idRule.setCurNum(Integer.valueOf(CurNum));
        }
        String num = idRule.getNumName()+date1+CurNum;
        idRuleMapper.updateByPrimaryKeySelective(idRule);
        logger.info("按指定规则生成的编号："+num);
        return num ;
    }

    /**
     * 根据num_count生产对应位数的last_num
     * @param num
     * @return
     */
    public static String GenerateDigit(int num){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <num ; i++) {
            sb.append(0);
        }
        return sb.toString();
    }
}
