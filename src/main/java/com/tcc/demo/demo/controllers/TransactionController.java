package com.tcc.demo.demo.controllers;

import com.alibaba.fastjson.JSONObject;
import com.tcc.demo.demo.modual.OrderContext;
import com.tcc.demo.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lw
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping(value = "/buy")
    public String commit(@RequestBody String param) {
        try {
            OrderContext orderContext = JSONObject.parseObject(param,OrderContext.class);
            service.buy(orderContext);
            return "commit success";
        } catch (RuntimeException e) {
            return "commit failed";
        }
    }
}
