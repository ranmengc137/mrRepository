package com.tcc.demo.demo.entities;

import java.util.Date;
import lombok.Data;

@Data
public class Account {
    private Long id;

    private String accountName;

    private Integer amountLeft;

    private Date updateTime;
}