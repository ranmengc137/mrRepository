package com.tcc.demo.demo.services;

/**
 * @author lw
 */
public interface Service {

    /**
     * tcc transaction try
     * @param success try type
     * @return try result
     */
    boolean prepare(boolean success);

    /**
     * tcc transaction confirm
     * @return confirm result
     */
    boolean commit();

    /**
     * tcc transaction cancel
     * @return confirm result
     */
    boolean cancel();
}
