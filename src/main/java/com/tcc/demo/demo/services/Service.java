package com.tcc.demo.demo.services;

import com.tcc.demo.demo.modual.OrderContext;

import java.util.Map;

/**
 * @author lw
 */
public interface Service {

    /**
     * tcc transaction try
     * @param success try type
     * @return try result
     */
    boolean prepare(OrderContext context);

    /**
     * tcc transaction confirm
     * @return confirm result
     */
    boolean commit(String trxId);

    /**
     * tcc transaction cancel
     * @return confirm result
     */
    boolean cancel(String trxId);
}
