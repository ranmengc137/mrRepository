package com.tcc.demo.demo.services;

import com.tcc.demo.demo.annotation.TccTransaction;
import com.tcc.demo.demo.entities.Inventory;
import com.tcc.demo.demo.services.impl.AccountService;
import com.tcc.demo.demo.services.impl.InventoryService;
import com.tcc.demo.demo.transaction.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lw
 */
@Slf4j
@Component
public class TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private InventoryService inventoryService;

    @TccTransaction
    public void buySuccess(Map<String,Integer> request) {
        log.info("global transaction id:: " + RootContext.get());
        if (!accountService.prepare(request)) {
            log.info("user try failed");
            throw new RuntimeException("user prepare failed!");
        }
        log.info("user try success");
        if (!inventoryService.prepare(request)) {
            log.info("store try failed");
            throw new RuntimeException("store prepare failed");
        }
        log.info("store try success");
    }
}
