package com.chenjing.apisecurity.filter;

import com.chenjing.apisecurity.ApiProperties;
import com.chenjing.apisecurity.ProductProvider;
import com.chenjing.apisecurity.decrypt.Decrypt;
import com.chenjing.apisecurity.util.SpringActiveUtils;
import com.chenjing.apisecurity.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 数据解密
 *
 * @author Chenjing
 * @date 2018/12/29
 */
@Slf4j
public class DecryptFilter implements Filter {

    private ApiProperties.DecryptProperties decryptProperties;

    private Decrypt decrypt;

    private SpringActiveUtils springActiveUtils;

    private ProductProvider productProvider;

    public DecryptFilter(ApiProperties apiProperties, Decrypt decrypt, SpringActiveUtils springActiveUtils,
                         ProductProvider productProvider) {
        this.decryptProperties = apiProperties.getDecrypt();
        this.decrypt = decrypt;
        this.springActiveUtils = springActiveUtils;
        this.productProvider = productProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("begin decrypt filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        String payload = requestWrapper.getPayloadAsString();
        if (springActiveUtils.isNeedDecrypt(decryptProperties.getExcludeProfiles())) {
            String decryptData;
            try {
                decryptData = decrypt.decrypt(payload, productProvider.getDecryptKey(request));
            } catch (Exception e) {
                log.error("decrypt fail by {}", e.getMessage(), e);
                // TODO: 2018/12/29  
                throw new RuntimeException(e);
            }
            log.debug("origin data ={} ,decrypt data ={}", payload, decryptData);
            requestWrapper.setPayload(decryptData.getBytes());
        }
        filterChain.doFilter(requestWrapper, response);
    }
}
