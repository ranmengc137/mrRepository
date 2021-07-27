package com.tcc.demo.demo.entities;

import java.util.Date;
import lombok.Data;

@Data
public class InventoryTcc {
    private Long id;

    private String prodName;

    private Integer inventoryFree;

    private Date updatetime;

    /**
    * 0-default;1-prepare;2-commit;3-cancle
    */
    private String status;

    private String trxId;
}