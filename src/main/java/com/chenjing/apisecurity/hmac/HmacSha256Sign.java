package com.chenjing.apisecurity.hmac;

import com.chenjing.apisecurity.wrapper.HttpRequestWrapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * 构建服务端的签名
 *
 * @author Chenjing
 * @date 2018/12/27
 */
@Slf4j
public class HmacSha256Sign implements SignBuilder {

    private static final String DELIMITER = "\n";

    @Override
    public String build(String encryptKey, HttpRequestWrapper httpRequestWrapper) throws IOException {
        Preconditions.checkNotNull(encryptKey, "encryptKey should not be null");
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("method", httpRequestWrapper.getMethod());
        parameters.put("requestURI", httpRequestWrapper.getRequestURI());
        String queryString = httpRequestWrapper.getQueryString();
        parameters.put("queryString", queryString == null ? "" : queryString);
        parameters.put("payload", httpRequestWrapper.getPayloadAsString());
        String prepared = String.join(DELIMITER, parameters.values());
        log.debug("The data to sign:\n{}", prepared);
        return Base64.encodeBase64URLSafeString(HmacUtils.hmacSha256(encryptKey, prepared));
    }
}
