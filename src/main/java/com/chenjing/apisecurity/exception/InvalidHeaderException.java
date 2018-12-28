package com.chenjing.apisecurity.exception;

/**
 * sign doesn't exception
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public class InvalidHeaderException extends RuntimeException {

    public InvalidHeaderException(String headerName) {
        super("header name = " + headerName + " is not exist");
    }
}
