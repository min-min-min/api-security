package com.chenjing.apisecurity.hmac;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import java.util.LinkedHashMap;

/**
 * @author Chenjing
 * @date 2018/12/27
 */
@Slf4j
public class HmacSha256Sign implements Sign {

    private static final String DELIMITER = "\n";

    @Override
    public String generate(String encryptKey, LinkedHashMap<String, String> params, String algorithm) {
        Preconditions.checkNotNull(encryptKey, "encryptKey should not be null");
        Preconditions.checkNotNull(params, "params should not be null");
        Preconditions.checkNotNull(algorithm, "algorithm should not be null");

        String prepared = String.join(DELIMITER, params.values());
        log.debug("The data to sign:\n{}", prepared);

        return Base64.encodeBase64URLSafeString(HmacUtils.hmacSha256(encryptKey, prepared));
    }
}
