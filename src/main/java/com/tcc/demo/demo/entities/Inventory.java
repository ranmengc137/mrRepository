package com.tcc.demo.demo.entities;

import java.util.Date;
import lombok.Data;

@Data
public class Inventory {
    private Long id;

    private String prodName;

    private Integer inventoryLeft;

    private Date updatetime;
}