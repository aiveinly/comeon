package com.planet.test.service;

import com.planet.test.dao.AccountMapper;
import com.planet.test.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/1/13.
 */
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private AccountMapper accountMapper;

    public int addId() {
        Account account  = new Account();
        account.setUserName("aaa");
        account.setPassword("bbb");
        return accountMapper.insertSelective(account);
    }

    public List<Integer> searchAllId(Map map) {
        return accountMapper.listPageIds(map);
    }
}
