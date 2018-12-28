package com.chenjing.apisecurity.exception;

/**
 * sign doesn't exception
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String name) {
        super("url param = " + name + " is not exist");
    }
}
