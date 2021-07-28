package com.tcc.demo.demo.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 用于分支事务的服务注册
 * 标识try、confirm、cancel方法
 * @author lw
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(1)
public @interface TccAction {

    String name();

    String confirmMethod();

    String cancelMethod();
}
