package com.tcc.demo.demo.entities;

public enum StatusEnum {

    DEFAULT("0",""),
    PREPARE("1",""),
    COMMIT("2",""),
    CANCEL("3",""),

    ;

    private String code;
    private String msg;

    private StatusEnum(String code,String msg){}

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
