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
        System.out.println("global transaction id:: " + RootContext.get());
        if (!accountService.prepare(context)) {
            System.out.println("accountService try failed");
            throw new RuntimeException("accountService prepare failed!");
        }

        System.out.println("accountService try success");
        if (!inventoryService.prepare(context)) {
            System.out.println("inventoryService try failed");
            throw new RuntimeException("inventoryService prepare failed");
        }
        System.out.println("inventoryService try success");
    }
}
