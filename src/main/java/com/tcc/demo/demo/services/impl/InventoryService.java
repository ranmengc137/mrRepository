package com.tcc.demo.demo.services.impl;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.annotation.TccAction;
import com.tcc.demo.demo.entities.Inventory;
import com.tcc.demo.demo.entities.InventoryTcc;
import com.tcc.demo.demo.entities.StatusEnum;
import com.tcc.demo.demo.mappers.InventoryMapper;
import com.tcc.demo.demo.mappers.InventoryTccMapper;
import com.tcc.demo.demo.modual.OrderContext;
import com.tcc.demo.demo.modual.RootContext;
import com.tcc.demo.demo.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@DS("inventory")
public class InventoryService implements Service {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryTccMapper inventoryTccMapper;


    @Override
    @TccAction(name = "prepare", confirmMethod = "commit", cancelMethod = "cancel")
    @DS("inventory")
    public boolean prepare(OrderContext context) {
        // prepare逻辑 扣减账户金额，在tcc表冻结金额
        Inventory inventory = null;
        inventory = inventoryMapper.selectByPrimaryKey((long) 1);
        int inventoryLeft = inventory.getInventoryLeft() - context.getCount();
        if(inventoryLeft < 0){
            return false;
        }else{
            int j = inventoryMapper.deductInventory("apple", inventoryLeft);
            int i = inventoryTccMapper.freezeInventory(getInventoryTcc(context));
            if(i + j < 2){
                throw new RuntimeException();
            }
        }
        return true;
    }

    private InventoryTcc getInventoryTcc(OrderContext context) {
        InventoryTcc inventoryTcc = new InventoryTcc();
        inventoryTcc.setTrxId(RootContext.get());
        inventoryTcc.setProdName("apple");
        inventoryTcc.setStatus(StatusEnum.PREPARE.getCode());
        inventoryTcc.setUpdatetime(new Date());
        inventoryTcc.setInventoryFree(context.getCount());
        return inventoryTcc;
    }

    @Override
    @DS("inventory")
    public boolean commit(String trxId) {
        // commit逻辑，修改tcc状态
        int i = inventoryTccMapper.updateStatusToCommit(trxId);
        if(i < 1){
            return false;
        }
        return true;
    }

    @Override
    @DS("inventory")
    public boolean cancel(String trxId) {
        try{
            // cancel逻辑，修改tcc状态
            int i = inventoryTccMapper.updateStatusToCancel(trxId);
            int j = inventoryTccMapper.unFreeze(trxId);
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
