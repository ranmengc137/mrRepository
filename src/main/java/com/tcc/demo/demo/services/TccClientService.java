package com.tcc.demo.demo.services;

import com.tcc.demo.demo.annotation.DS;
import com.tcc.demo.demo.constant.TransactionMethod;
import com.tcc.demo.demo.constant.TransactionStatus;
import com.tcc.demo.demo.entities.TransactionInfo;
import com.tcc.demo.demo.mappers.TransactionInfoMapper;
import com.tcc.demo.demo.utils.SpringBeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有点类似分支事务管理器 TM
 * @author lw
 */
@Service
@Slf4j
@DS("test")
public class TccClientService {

    private final TransactionInfoMapper transactionInfoMapper;

    public TccClientService(TransactionInfoMapper transactionInfoMapper) {
        this.transactionInfoMapper = transactionInfoMapper;
    }

    /**
     * 收到 全局事务管理器（TC）的信息后执行
     * 查询数据库，存在一个分支事务失败状态则进入 cancel，全成功则进入 confirm 阶段
     * @param xid xid
     * @return 返回 confirm 或者 cancel 的执行结果
     */
    public boolean transactionHandle(String xid) {
        // 根据 xid 查询出所有的分支事务信息
        Map<String, Object> condition = new HashMap<>(1);
        condition.put("xid", xid);
        List<Map<String, Object>> branchTransactions = transactionInfoMapper.query(condition);

        // 判断是否所有事务的 try 都执行成功，如果成功则 confirm，反之 cancel
        boolean executeConfirm = true;
        for (Map<String, Object> item: branchTransactions) {
            if (item.get("status").equals(TransactionStatus.TRY_FAILED) || item.get("status").equals(TransactionStatus.CONFIRM_FAILED)) {
                executeConfirm = false;
                break;
            }
        }
        // 执行 confirm 或者 cancel
        if (executeConfirm) {
            return executeMethod(branchTransactions, TransactionMethod.CONFIRM,xid);
        } else {
            return executeMethod(branchTransactions, TransactionMethod.CANCEL,xid);
        }
    }

    /**
     * 通过分支事务注册的 类名和方法名，反射调用相应的 confirm 或者 cancel 方法
     * 这里是串行的，也可以使用线程池进行并行操作
     * @param branchTransactions 分支事务信息
     * @param methodName confirm 或者 cancel
     * @return bool
     */
    private boolean executeMethod(List<Map<String, Object>> branchTransactions, String methodName,String trxId) {
        for (Map<String, Object> item: branchTransactions) {
            log.info("service info:: " + item.toString());
            log.info("service method :: " + item.get(methodName).toString());

            try {
                Class<?> clazz = Class.forName(item.get("class_name").toString());
                log.info("Service Class::" + clazz.getName());

                Method method = clazz.getDeclaredMethod(item.get(methodName).toString(),String.class);
                log.info("Service Method::" + method.toString());

                Object service = SpringBeanFactoryUtils.getBean(clazz);
                Object ret = method.invoke(service,trxId);
                log.info("execute method return: " + ret.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 分支事务信息注册
     * @param xid xid
     * @param className 服务类名
     * @param commitMethodName confirm 方法名
     * @param cancelMethodName cancel 方法名
     */
    public void register(String xid, String className, String commitMethodName, String cancelMethodName) {
        log.info("Register xid::" + xid + " class name:: " + className + " commit method::" + commitMethodName +
                " cancel method::" + cancelMethodName);

        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setXid(xid);
        transactionInfo.setStatus(TransactionStatus.TRY_RUNNING);
        transactionInfo.setClassName(className);
        transactionInfo.setCommitMethodName(commitMethodName);
        transactionInfo.setCancelMethodName(cancelMethodName);
        transactionInfoMapper.insertOne(transactionInfo);

        Map<String, Object> condition = new HashMap<>(1);
        condition.put("xid", xid);
        List<Map<String, Object>> transactionInfos = transactionInfoMapper.query(condition);
        log.info("insert to database:: " + transactionInfos.toString());
    }
}
