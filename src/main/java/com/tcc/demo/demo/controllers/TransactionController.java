package com.tcc.demo.demo.controllers;

import com.tcc.demo.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lw
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping(value = "/commit")
    public String commit() {
        try {
            service.buySuccess(new HashMap<>());
            return "commit success";
        } catch (RuntimeException e) {
            return "commit failed";
        }
    }
}
