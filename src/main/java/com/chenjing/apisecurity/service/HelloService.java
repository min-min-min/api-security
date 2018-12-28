package com.chenjing.apisecurity.service;

/**
 * @author Chenjing
 * @date 2018/12/27
 */
public class HelloService {
    private String msg;
    public String sayHello(){
        return "hello " + msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
