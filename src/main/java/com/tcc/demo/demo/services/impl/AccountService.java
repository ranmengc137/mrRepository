package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.annotation.TccAction;
import com.tcc.demo.demo.entities.Account;
import com.tcc.demo.demo.entities.AccountTcc;
import com.tcc.demo.demo.entities.StatusEnum;
import com.tcc.demo.demo.mappers.AccountMapper;
import com.tcc.demo.demo.mappers.AccountTccMapper;
import com.tcc.demo.demo.modual.OrderContext;
import com.tcc.demo.demo.services.Service;
import com.tcc.demo.demo.modual.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AccountService implements Service {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountTccMapper accountTccMapper;


    @Override
    @TccAction(name = "prepare", confirmMethod = "commit", cancelMethod = "cancel")
    @DS("account")
    public boolean prepare(OrderContext context) {
        // prepare逻辑 扣减账户金额，在tcc表冻结金额
        Account account = null;
        account = accountMapper.selectByPrimaryKey((long) 1);
        int amountLeft = account.getAmountLeft() - context.getAmount();
        if(amountLeft < 0){
            return false;
        }else{
            int j = accountMapper.deductAmount("zhangsan", amountLeft);
            int i = accountTccMapper.freezeAmount(getAccountTcc(context));
            if(i + j < 2){
                throw new RuntimeException();
            }
        }
        return true;
    }

    private AccountTcc getAccountTcc(OrderContext context) {
        AccountTcc accountTcc = new AccountTcc();
        accountTcc.setTrxId(RootContext.get());
        accountTcc.setAccountName("zhangsan");
        accountTcc.setStatus(StatusEnum.PREPARE.getCode());
        accountTcc.setUpdatetime(new Date());
        accountTcc.setAmountFreeze(context.getAmount());
        return accountTcc;
    }

    @Override
    @DS("account")
    public boolean commit(String trxId) {
        // commit逻辑，修改tcc状态
        int i = accountTccMapper.updateStatusToCommit(trxId);
        if(i < 1){
            return false;
        }
        return true;
    }

    @Override
    @DS("account")
    public boolean cancel(String trxId) {
        try{
            // cancel逻辑，修改tcc状态
            int i = accountTccMapper.updateStatusToCancel(trxId);
            int j = accountTccMapper.unFreeze(trxId);
            if(i + j < 2){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
