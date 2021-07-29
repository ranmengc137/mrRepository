package com.tcc.demo.demo.services;

import com.tcc.demo.demo.annotation.TccTransaction;
import com.tcc.demo.demo.modual.OrderContext;
import com.tcc.demo.demo.services.impl.AccountService;
import com.tcc.demo.demo.services.impl.InventoryService;
import com.tcc.demo.demo.modual.RootContext;
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

    @Autowired
    private InventoryService inventoryService;

    @TccTransaction
    public void buy(OrderContext context) {
        log.info("global transaction id:: " + RootContext.get());
        if (!accountService.prepare(context)) {
            log.info("accountService try failed");
            throw new RuntimeException("accountService prepare failed!");
        }

        log.info("accountService try success");
        if (!inventoryService.prepare(context)) {
            log.info("inventoryService try failed");
            throw new RuntimeException("inventoryService prepare failed");
        }
        log.info("inventoryService try success");
    }
}
