package com.tcc.demo.demo.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Order(2)
public @interface DS {
    /**
     * 数据源名称
     */
    String value() default "test";
}