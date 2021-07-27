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
public class UserAccountServiceImpl implements Service {

    @Override
    @TccAction(name = "prepare", confirmMethod = "commit", cancelMethod = "cancel")
    public boolean prepare(boolean success) {
        System.out.println("user prepare");
        System.out.println("global transaction id:: " + RootContext.get());
        if (success) {
            System.out.println("User prepare success");
        } else {
            System.out.println("User prepare failed");
        }
        return success;
    }

    @Override
    public boolean commit() {
        System.out.println("User commit");
        System.out.println("global transaction id:: " + RootContext.get());
        return true;
    }

    @Override
    public boolean cancel() {
        System.out.println("User cancel");
        System.out.println("global transaction id:: " + RootContext.get());
        return true;
    }
}
