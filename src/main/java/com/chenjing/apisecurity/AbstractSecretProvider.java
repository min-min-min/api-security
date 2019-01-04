package com.chenjing.apisecurity;

import com.chenjing.apisecurity.decrypt.Decrypt;
import com.chenjing.apisecurity.encrypt.Encrypt;
import com.chenjing.apisecurity.util.DesUtils;
import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

/**
 * 可继承该类并交给spring管理
 * 自定义自己的加解密方法
 *
 * @author Chenjing
 * @date 2018/12/29
 */
public abstract class AbstractSecretProvider implements Decrypt, Encrypt {

    private static final String EMPTY_STRING = "";

    @Override
    public String decrypt(String payload, String key) throws Exception {
        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
        if (StringUtils.hasText(payload)) {
            return DesUtils.decrypt(payload, key);
        }
        return EMPTY_STRING;
    }

    @Override
    public String encrypt(String response, String key) throws Exception {
        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
        if (StringUtils.hasText(response)) {
            return DesUtils.encrypt(response, key);
        }
        return EMPTY_STRING;
    }
}
