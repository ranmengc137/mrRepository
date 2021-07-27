package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.mappers.InventoryMapper;
import com.tcc.demo.demo.mappers.InventoryTccMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("inventory")
public class InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryTccMapper inventoryTccMapper;


}
