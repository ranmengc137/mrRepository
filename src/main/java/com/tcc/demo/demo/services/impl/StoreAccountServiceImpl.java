package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.TccAction;
import com.tcc.demo.demo.services.Service;
import com.tcc.demo.demo.transaction.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lw
 */
@Component
@Slf4j
public class StoreAccountServiceImpl implements Service {

    @Override
    @TccAction(name = "prepare", confirmMethod = "commit", cancelMethod = "cancel")
    public boolean prepare(boolean success) {
        System.out.println("store prepare");
        System.out.println("global transaction id:: " + RootContext.get());
        if (success) {
            System.out.println("Store prepare success");
        } else {
            System.out.println("Store prepare failed");
        }
        return success;
    }

    @Override
    public boolean commit() {
        System.out.println("Store commit");
        System.out.println("global transaction id:: " + RootContext.get());
        return true;
    }

    @Override
    public boolean cancel() {
        System.out.println("Store cancel");
        System.out.println("global transaction id:: " + RootContext.get());
        return true;
    }
}
