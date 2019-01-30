package com.chenjing.apisecurity;

import com.chenjing.apisecurity.encrypt.Encrypt;
import com.chenjing.apisecurity.filter.EncryptFilter;
import com.chenjing.apisecurity.util.SpringActiveUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author Chenjing
 * @date 2018/12/29
 */
@Configuration
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnProperty(prefix = "api.encrypt", value = "enabled", havingValue = "true")
@Slf4j
@Import(SpringActiveUtils.class)
public class EncryptAutoConfiguration {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private Encrypt encrypt;

    @Autowired
    private SpringActiveUtils springActiveUtils;

    @Autowired
    private ProductProvider productProvider;

    @Bean
    public FilterRegistrationBean encryptFilter() {
        log.info("init encrypt filter");
        EncryptFilter decryptFilter = new EncryptFilter(apiProperties, encrypt, springActiveUtils, productProvider);
        FilterRegistrationBean<EncryptFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(decryptFilter);
        registration.addUrlPatterns(apiProperties.getEncrypt().getUrlPatterns().toArray(new String[0]));
        registration.setName("encryptFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE - 2);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(Encrypt.class)
    public Encrypt encrypt() {
        log.info("init System Encrypt");
        return new SecretProviderImpl();
    }
}
