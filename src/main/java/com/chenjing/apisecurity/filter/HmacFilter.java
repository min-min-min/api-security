package com.chenjing.apisecurity.filter;

import com.chenjing.apisecurity.ApiProperties;
import com.chenjing.apisecurity.Product;
import com.chenjing.apisecurity.ProductProvider;
import com.chenjing.apisecurity.exception.InvalidHeaderException;
import com.chenjing.apisecurity.exception.InvalidParamException;
import com.chenjing.apisecurity.exception.InvalidSignException;
import com.chenjing.apisecurity.exception.ProductException;
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

    public HmacFilter(SignBuilder signBuilder, ApiProperties apiProperties, Environment environment,
                      ProductProvider productProvider) {
        this.signBuilder = signBuilder;
        this.hmacProperties = apiProperties.getHmac();
        this.environment = environment;
        this.productProvider = productProvider;
    }

    private SignBuilder signBuilder;

    private ApiProperties.HmacProperties hmacProperties;

    private Environment environment;

    private ProductProvider productProvider;

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
        String productKeyValue = requestWrapper.getParameter(hmacProperties.getProductUrlParamName());
        if (productKeyValue == null) {
            throw new InvalidParamException(hmacProperties.getProductUrlParamName());
        }
        Product product = productProvider.getProduct(productKeyValue);
        if (product == null) {
            throw new ProductException(productKeyValue);
        }
        String serverSign = signBuilder.build(product.getEncryptKey(), requestWrapper);
        String clientSign = requestWrapper.getHeader(hmacProperties.getHeaderName());
        if (clientSign == null) {
            throw new InvalidHeaderException(hmacProperties.getHeaderName());
        }
        log.debug("server sign = {},client sign = {}", serverSign, clientSign);
        if (!Objects.equals(serverSign, clientSign)) {
            throw new InvalidSignException();
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
