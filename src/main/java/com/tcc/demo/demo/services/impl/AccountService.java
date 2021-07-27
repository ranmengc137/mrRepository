package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.annotation.TccAction;
import com.tcc.demo.demo.mappers.AccountMapper;
import com.tcc.demo.demo.mappers.AccountTccMapper;
import com.tcc.demo.demo.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@DS("account")
public class AccountService implements Service {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountTccMapper accountTccMapper;


    @Override
    @TccAction(name = "prepare", confirmMethod = "commit", cancelMethod = "cancel")
    public boolean prepare(Map<String,Integer> request) {
        return false;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public boolean cancel() {
        return false;
    }
}
