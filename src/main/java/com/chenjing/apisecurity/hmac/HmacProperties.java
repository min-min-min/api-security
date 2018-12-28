package com.chenjing.apisecurity.hmac;

import lombok.Data;

/**
 * hmac config properties
 *
 * @author Chenjing
 * @date 2018/12/27
 */
@Data
public class HmacProperties {

    /**
     * enabled or disable hmac sign
     * default {@code true}
     */
    private boolean enabled = true;

    /**
     * product name
     * e.g. get {@code privateKey} from product name
     */
    private String productName;

    /**
     * for encrypt data
     */
    private String encryptKey;
}
