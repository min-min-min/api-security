package com.chenjing.apisecurity;

import com.chenjing.apisecurity.hmac.HmacSha256Sign;
import com.chenjing.apisecurity.hmac.Sign;
import com.chenjing.apisecurity.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnClass(HelloService.class)
@ConditionalOnProperty(prefix = "api.hmac", value = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class HmacAutoConfiguration {

    @Autowired
    private ApiProperties apiProperties;

    @ConditionalOnMissingBean(Sign.class)
    @Bean
    public Sign sign() {
        return new HmacSha256Sign();
    }
}

