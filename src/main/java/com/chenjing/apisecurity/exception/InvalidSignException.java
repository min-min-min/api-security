package com.chenjing.apisecurity.exception;

/**
 * sign doesn't exception
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public class InvalidSignException extends RuntimeException {

    public InvalidSignException() {
        super("the client sign doesn't match server sign");
    }
}
