package com.chenjing.apisecurity.hmac;

import com.google.common.net.HttpHeaders;
import lombok.Data;

import java.util.List;

/**
 * hmac config properties
 *
 * @author Chenjing
 * @date 2018/12/27
 */
@Data
public class HmacProperties {

    private String encryptKey;

    private List<String> urlPatterns;

    private String headerName = HttpHeaders.AUTHORIZATION;

    private List<String> excludeProfiles;
}
