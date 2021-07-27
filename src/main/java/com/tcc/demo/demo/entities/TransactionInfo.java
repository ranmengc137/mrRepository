package com.tcc.demo.demo.entities;

import lombok.Data;

@Data
public class TransactionInfo {
    private Integer id;

    private String xid;

    private Integer status;

    private String className;

    private String commitMethodName;

    private String cancelMethodName;
}