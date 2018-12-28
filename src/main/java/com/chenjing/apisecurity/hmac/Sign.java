package com.chenjing.apisecurity.hmac;

import java.util.LinkedHashMap;

/**
 * default HmacSha256 sign
 *
 * @author Chenjing
 * @date 2018/12/27
 */
public interface Sign {

    String generate(String encryptKey, LinkedHashMap<String, String> params, String algorithm);
}
