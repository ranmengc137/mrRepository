package com.tcc.demo.demo.mappers;

import com.tcc.demo.demo.entities.TransactionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lw
 */
@Repository
public interface TransactionInfoMapper {

    /**
     * insert one
     * @param transactionInfo entity
     */
    void insertOne(TransactionInfo transactionInfo);

    /**
     * update one
     * @param transactionInfo entity
     */
    void updateOne(TransactionInfo transactionInfo);

    /**
     * query
     * @param condition query condition
     * @return result list
     */
    List<Map<String, Object>> query(Map<String, Object> condition);
}
