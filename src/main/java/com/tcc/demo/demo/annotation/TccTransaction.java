package com.tcc.demo.demo.annotation;

import java.lang.annotation.*;

/**
 * 用于全局事务的注册处理
 * @author lw
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TccTransaction {
}
