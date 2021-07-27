package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.mappers.InventoryMapper;
import com.tcc.demo.demo.mappers.InventoryTccMapper;
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
