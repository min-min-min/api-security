package com.chenjing.apisecurity.filter;

import com.chenjing.apisecurity.ApiProperties;
import com.chenjing.apisecurity.ProductProvider;
import com.chenjing.apisecurity.exception.InvalidHeaderException;
import com.chenjing.apisecurity.exception.InvalidSignException;
import com.chenjing.apisecurity.hmac.SignBuilder;
import com.chenjing.apisecurity.util.SpringActiveUtils;
import com.chenjing.apisecurity.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * hmac验签过滤器
 *
 * @author Chenjing
 * @date 2018/12/28
 */
@Slf4j
public class HmacFilter implements Filter {

    public HmacFilter(SignBuilder signBuilder, ApiProperties apiProperties, SpringActiveUtils springActiveUtils,
                      ProductProvider productProvider) {
        this.signBuilder = signBuilder;
        this.hmacProperties = apiProperties.getHmac();
        this.springActiveUtils = springActiveUtils;
        this.productProvider = productProvider;
    }

    private SignBuilder signBuilder;

    private ApiProperties.HmacProperties hmacProperties;

    private SpringActiveUtils springActiveUtils;

    private ProductProvider productProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("begin hmac filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(req);
        if (!springActiveUtils.isIgnoredProfiles(hmacProperties.getExcludeProfiles())) {
            validateSign(requestWrapper,req);
        }
        chain.doFilter(requestWrapper, resp);
    }

    private void validateSign(HttpRequestWrapper requestWrapper,HttpServletRequest request) throws IOException {
        log.debug("payload is  {}", requestWrapper.getPayloadAsString());

        String serverSign = signBuilder.build(productProvider.getHmacKey(request), requestWrapper);
        String clientSign = Optional.ofNullable(requestWrapper.getHeader(hmacProperties.getHeaderName()))
                .orElseThrow(() -> new InvalidHeaderException(hmacProperties.getHeaderName()));

        log.debug("server sign = {},client sign = {}", serverSign, clientSign);
        if (!Objects.equals(serverSign, clientSign)) {
            throw new InvalidSignException();
        }
    }
}
