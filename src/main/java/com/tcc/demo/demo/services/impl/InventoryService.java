package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.mappers.InventoryMapper;
import com.tcc.demo.demo.mappers.InventoryTccMapper;
import com.tcc.demo.demo.modual.OrderContext;
import com.tcc.demo.demo.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@DS("inventory")
public class InventoryService implements Service {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryTccMapper inventoryTccMapper;


    @Override
    public boolean prepare(OrderContext context) {
        return false;
    }

    @Override
    public boolean commit(String trxId) {
        return false;
    }

    @Override
    public boolean cancel(String trxId) {
        return false;
    }
}
