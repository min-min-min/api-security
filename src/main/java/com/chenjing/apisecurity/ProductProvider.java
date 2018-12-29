package com.chenjing.apisecurity;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Chenjing
 * @date 2018/12/28
 */
public interface ProductProvider {

    String getHmacKey(HttpServletRequest request);

    String getEncryptKey(HttpServletRequest request);

    String getDecryptKey(HttpServletRequest request);
}
