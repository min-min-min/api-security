package com.chenjing.apisecurity.filter;

import com.chenjing.apisecurity.ApiProperties;
import com.chenjing.apisecurity.exception.InvalidHeaderException;
import com.chenjing.apisecurity.exception.InvalidSignException;
import com.chenjing.apisecurity.hmac.HmacProperties;
import com.chenjing.apisecurity.hmac.SignBuilder;
import com.chenjing.apisecurity.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * hmac验签过滤器
 *
 * @author Chenjing
 * @date 2018/12/28
 */
@Slf4j
public class HmacFilter implements Filter {

    public HmacFilter(SignBuilder signBuilder, ApiProperties apiProperties, Environment environment) {
        this.signBuilder = signBuilder;
        this.hmacProperties = apiProperties.getHmac();
        this.environment = environment;
    }

    private SignBuilder signBuilder;

    private HmacProperties hmacProperties;

    private Environment environment;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("begin hmac filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(req);
        if (!isIgnoredProfiles()) {
            validateSign(requestWrapper);
        }
        chain.doFilter(requestWrapper, resp);
    }

    private void validateSign(HttpRequestWrapper requestWrapper) throws IOException {
        log.debug("payload is  {}", requestWrapper.getPayloadAsString());
        String serverSign = signBuilder.build(hmacProperties.getEncryptKey(), requestWrapper);
        String clientSign = requestWrapper.getHeader(hmacProperties.getHeaderName());
        if (clientSign == null) {
            throw new InvalidHeaderException(hmacProperties.getHeaderName());
        }
        log.debug("server sign = {},client sign = {}", serverSign, clientSign);
        if (!Objects.equals(serverSign, clientSign)) {
            throw new InvalidSignException(serverSign);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isIgnoredProfiles() {
        List<String> excludeProfiles = hmacProperties.getExcludeProfiles();
        if (excludeProfiles != null && !excludeProfiles.isEmpty()) {
            log.debug("Profile(s) {} will skip hmac function", excludeProfiles);
            return environment.acceptsProfiles(Profiles.of(excludeProfiles.toArray(new String[0])));
        } else {
            return false;
        }
    }
}
