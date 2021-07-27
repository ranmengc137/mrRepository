package com.tcc.demo.demo.entities;

import java.util.Date;
import lombok.Data;

@Data
public class AccountTcc {
    private Long id;

    private String accountName;

    private Integer amountFreeze;

    private Date updatetime;

    /**
    * 0-default;1-prepare;2-commit;3-cancle
    */
    private String status;

    private String trxId;
}