package com.tcc.demo.demo.controllers;

import com.tcc.demo.demo.services.TccClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TM
 * @author lw
 */
@RestController
@RequestMapping("/tm")
public class TccClientController {

    @Autowired
    private TccClientService tccClientService;

    @GetMapping("/tryNext")
    public boolean tryNextStep(@RequestParam(value = "xid") String xid) {
        return tccClientService.transactionHandle(xid);
    }
}
