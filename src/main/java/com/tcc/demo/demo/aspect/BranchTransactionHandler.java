package com.tcc.demo.demo.aspect;

import com.tcc.demo.demo.annotation.TccAction;
import com.tcc.demo.demo.services.TccClientService;
import com.tcc.demo.demo.modual.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分支事务处理注解 {@TCCAction} 拦截处理
 * 注册try方法的调用到全局事务管理器中
 * @author lw
 */
@Aspect
@Component
@Slf4j
public class BranchTransactionHandler {

    private final TccClientService tccClientService;

    public BranchTransactionHandler(TccClientService tccClientService) {
        this.tccClientService = tccClientService;
    }

    @Pointcut(value = "@annotation(com.tcc.demo.demo.annotation.TccAction)")
    public void branchTransaction() {}

    @Before("branchTransaction()")
    public void branchTransactionHandler(JoinPoint point) {
        log.info("Branch transaction handler :: " + RootContext.get());

        // 获取分支事务服务类名，用于后面反射类加载
        Object target = point.getTarget().getClass();
        String className = ((Class) target).getName();

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        TccAction tccActionAnnotation = method.getAnnotation(TccAction.class);

        // 获取 confirm 和 cancel 的对应方法名称
        String commitMethodName = tccActionAnnotation.confirmMethod();
        String cancelMethodName = tccActionAnnotation.cancelMethod();

        // 写入全局事务管理的数据中
        tccClientService.register(RootContext.get(), className, commitMethodName, cancelMethodName);
    }
}
