package com.chenjing.apisecurity.hmac;

import com.chenjing.apisecurity.wrapper.HttpRequestWrapper;

import java.io.IOException;

/**
 * 构建服务端签名，默认实现采用hmacSha256
 * 优先采用接入方实现的这个接口进行签名，如果用户没有实现，则使用{@link HmacSha256Sign}
 *
 * @author Chenjing
 * @date 2018/12/27
 */
public interface SignBuilder {

    /**
     * 以下请求：
     * POST /v1/test?type=any&timestamp=1472032612159 HTTP/1.1
     * Host: xx.gomo.com
     * X-Signature: Signature
     * Content-Type: application/json
     * {"key1":"value1","key2":value2}
     * <p>
     * <p>
     * 则对以下内容加密，一定是四个参数，如果没有 则用空字符串{@code ""}替代：
     * POST
     * /v1/test
     * type=any&timestamp=1472032612159
     * {"key1":"value1","key2":value2}
     *
     * @param encryptKey         加密密钥
     * @param httpRequestWrapper httpRequest
     * @return 加密后的字符串
     * @throws IOException
     */
    String build(String encryptKey, HttpRequestWrapper httpRequestWrapper) throws IOException;
}
