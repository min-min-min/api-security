package com.chenjing.apisecurity.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Chenjing
 * @date 2018/12/27
 */
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {
    private static final String MSG = "world";
    private String msg = MSG;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}