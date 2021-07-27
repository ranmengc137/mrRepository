package com.tcc.demo.demo.annotation;

import java.lang.annotation.*;

/**
 * 用于分支事务的服务注册
 * 标识try、confirm、cancel方法
 * @author lw
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TccAction {

    String name();

    String confirmMethod();

    String cancelMethod();
}
