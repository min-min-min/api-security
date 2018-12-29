package com.chenjing.apisecurity.decrypt;

/**
 * @author Chenjing
 * @date 2018/12/29
 */
public interface Decrypt {
    String decrypt(String payload, String key) throws Exception;
}
