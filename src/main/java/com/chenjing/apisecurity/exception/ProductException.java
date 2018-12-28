package com.chenjing.apisecurity.exception;

/**
 * sign doesn't exception
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public class ProductException extends RuntimeException {

    public ProductException(String productKeyValue) {
        super("product key =" + productKeyValue + " is not exist");
    }
}
