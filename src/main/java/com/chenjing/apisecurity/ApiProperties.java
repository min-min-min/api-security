package com.chenjing.apisecurity;

import com.chenjing.apisecurity.hmac.HmacProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Chenjing
 * @date 2018/12/27
 */
@ConfigurationProperties(prefix = "api")
@Data
public class ApiProperties {

    private HmacProperties hmac;
}
