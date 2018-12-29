package com.chenjing.apisecurity;

import com.chenjing.apisecurity.decrypt.Decrypt;
import com.chenjing.apisecurity.filter.DecryptFilter;
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
@ConditionalOnProperty(prefix = "api.decrypt", value = "enabled", havingValue = "true")
@Slf4j
@Import(SpringActiveUtils.class)
public class DecryptAutoConfiguration {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private Decrypt decrypt;

    @Autowired
    private SpringActiveUtils springActiveUtils;

    @Autowired
    private ProductProvider productProvider;

    @Bean
    public FilterRegistrationBean decryptFilter() {
        log.info("init decrypt filter");
        DecryptFilter decryptFilter = new DecryptFilter(apiProperties, decrypt, springActiveUtils, productProvider);
        FilterRegistrationBean<DecryptFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(decryptFilter);
        registration.addUrlPatterns(apiProperties.getDecrypt().getUrlPatterns().toArray(new String[0]));
        registration.setName("decryptFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(Decrypt.class)
    public Decrypt decrypt() {
        log.debug("init System Decrypt...");
        return new SecretProviderImpl();
    }
}
