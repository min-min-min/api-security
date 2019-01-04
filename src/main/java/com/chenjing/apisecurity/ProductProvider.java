package com.chenjing.apisecurity;


import javax.servlet.http.HttpServletRequest;

/**
 * 设置加解密钥和验签的密钥
 *
 * @author Chenjing
 * @date 2018/12/28
 */
public interface ProductProvider {

    /**
     * 设置验签的密钥
     *
     * @param request http request
     * @return 验签密钥
     */
    String getHmacKey(HttpServletRequest request);

    /**
     * 设置加密密钥
     *
     * @param request http request
     * @return 加密密钥
     */
    String getEncryptKey(HttpServletRequest request);

    /**
     * 设置解密密钥
     *
     * @param request http request
     * @return 解密密钥
     */
    String getDecryptKey(HttpServletRequest request);
}
