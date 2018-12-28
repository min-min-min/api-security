package com.chenjing.apisecurity;

import com.chenjing.apisecurity.filter.HmacFilter;
import com.chenjing.apisecurity.hmac.HmacSha256Sign;
import com.chenjing.apisecurity.hmac.SignBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@EnableConfigurationProperties(ApiProperties.class)
@ConditionalOnProperty(prefix = "api.hmac", value = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class HmacAutoConfiguration {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private SignBuilder signBuilder;

    @Autowired
    private Environment environment;

    @Autowired
    private ProductProvider productProvider;

    @Bean
    public FilterRegistrationBean hmacFilter() {
        log.info("init hmac filter");
        HmacFilter hmacFilter = new HmacFilter(signBuilder, apiProperties, environment, productProvider);
        FilterRegistrationBean<HmacFilter> registration = new FilterRegistrationBean<>();
        List<String> urlPatterns = apiProperties.getHmac().getUrlPatterns();
        registration.addUrlPatterns(urlPatterns.toArray(new String[0]));
        registration.setFilter(hmacFilter);
        registration.setName("hmacFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean(SignBuilder.class)
    public SignBuilder hmacSha256sign() {
        log.info("init hmacSha256 sign");
        return new HmacSha256Sign();
    }
}

