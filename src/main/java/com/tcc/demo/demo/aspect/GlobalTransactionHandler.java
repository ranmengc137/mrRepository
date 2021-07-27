package com.tcc.demo.demo.aspect;

import com.tcc.demo.demo.constant.TransactionStatus;
import com.tcc.demo.demo.entities.TransactionInfo;
import com.tcc.demo.demo.mappers.TransactionInfoMapper;
import com.tcc.demo.demo.transaction.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * 全局事务{@TccTransacton} 拦截处理
 * 用于生成 全局事务 唯一标识ID，想事务管理器进行注册生成
 * @author lw
 */
@Aspect
@Component
@Slf4j
public class GlobalTransactionHandler {

    private final TransactionInfoMapper transactionInfoMapper;

    public GlobalTransactionHandler(TransactionInfoMapper transactionInfoMapper) {
        this.transactionInfoMapper = transactionInfoMapper;
    }

    @Pointcut("@annotation(com.tcc.demo.demo.annotation.TccTransaction)")
    public void globalTransaction() {}

    /**
     * 对全局事务进行拦截处理
     */
    @Around("globalTransaction()")
    public Object globalTransactionHandler(ProceedingJoinPoint point) throws UnknownHostException {
        log.info("Global transaction handler");

        // 生成全局事务ID，放入threadLocal中
        String transactionId = createTransactionId();
        RootContext.set(transactionId);

        try {
            // try 阶段的执行
            point.proceed();
        } catch (Throwable throwable) {
            // try 失败以后，在数据库中更新所有分支事务的状态
            log.info("global update transaction status to try failed");
            updateTransactionStatus(transactionId, TransactionStatus.TRY_FAILED);
            log.info("global update transaction status to try failed end");

            // 发送消息推动进入 cancel 阶段
            log.info(transactionId + " global transaction try failed, will rollback");
            sendTryMessage(transactionId);
            RootContext.remove();
            return null;
        }

        // try 成功，在数据库中更新所有分支事务的状态
        log.info("global update transaction status to try success");
        updateTransactionStatus(transactionId, TransactionStatus.TRY_SUCCESS);
        log.info("global update transaction status to try success end");

        // 发送消息推动进入 confirm 阶段，如果 confirm 失败，则再次发送消息推动进入 cancel 阶段
        log.info(transactionId + " global transaction try success, will confirm");
        if (!sendTryMessage(transactionId)) {
            log.info(transactionId + " global transaction confirm failed, will cancel");
            updateTransactionStatus(transactionId, TransactionStatus.CONFIRM_FAILED);
            sendTryMessage(transactionId);
            RootContext.remove();
            return null;
        }

        updateTransactionStatus(transactionId, TransactionStatus.CONFIRM_SUCCESS);
        RootContext.remove();
        return null;

    }

    /**
     * 发送消息到 分支事务管理器（TM）
     * TM 收到消息后，查询事务数据库，根据事务状态，判断执行 confirm 或者 cancel
     * 这里使用HTTP作为通信方式（为了简便，当然也可以使用其他的，如dubbo之类的）
     * @param transactionId xid
     * @return execute result
     */
    private boolean sendTryMessage(String transactionId) {
        log.info("send message to local TM to execute next step");
        String[] slice = transactionId.split(":");
        String targetHost = slice[0];
        String targetPort = slice[1];

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + targetHost + ":" + targetPort + "/tm/tryNext?xid=" + transactionId;
        Boolean response = restTemplate.getForObject(url, boolean.class, new HashMap<>(0));

        if (response == null || !response) {
            log.info("try next step execute failed, please manual check");
            return false;
        } else {
            log.info("try next step execute success");
            return true;
        }
    }

    /**
     * 生成全局事务ID：本机IP地址+本地分支事务管理器监听端口+时间戳
     * @return xid
     * @throws UnknownHostException UnknownHostException
     */
    private String createTransactionId() throws UnknownHostException {
        String localAddress = InetAddress.getLocalHost().getHostAddress();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return localAddress + ":8080:" + timeStamp;
    }

    /**
     * 根据 xid 更新 所有分支事务的执行状态
     * @param xid xid
     * @param status status
     */
    private void updateTransactionStatus(String xid, int status) {
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setXid(xid);
        transactionInfo.setStatus(status);
        try {
            transactionInfoMapper.updateOne(transactionInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
