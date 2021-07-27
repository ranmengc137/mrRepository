package com.tcc.demo.demo.services;

import com.tcc.demo.demo.annotation.TccTransaction;
import com.tcc.demo.demo.services.impl.AccountService;
import com.tcc.demo.demo.services.impl.StoreAccountServiceImpl;
import com.tcc.demo.demo.services.impl.UserAccountServiceImpl;
import com.tcc.demo.demo.transaction.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lw
 */
@Slf4j
@Component
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @TccTransaction
    public void buySuccess(int amount,int count) {
        log.info("global transaction id:: " + RootContext.get());
        if (!user.prepare(true)) {
            log.info("user try failed");
            throw new RuntimeException("user prepare failed!");
        }
        log.info("user try success");
        if (!store.prepare(true)) {
            log.info("store try failed");
            throw new RuntimeException("store prepare failed");
        }
        log.info("store try success");
    }

    @TccTransaction
    public void buyFailed() {
        log.info("global transaction id:: " + RootContext.get());
        if (!user.prepare(true)) {
            log.info("user try failed");
            throw new RuntimeException("user prepare failed!");
        }
        log.info("user try success");
        if (!store.prepare(false)) {
            log.info("store try failed");
            throw new RuntimeException("store prepare failed");
        }
        log.info("store try success");
    }
}
