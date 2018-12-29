package com.chenjing.apisecurity.filter;

import com.chenjing.apisecurity.ApiProperties;
import com.chenjing.apisecurity.ProductProvider;
import com.chenjing.apisecurity.encrypt.Encrypt;
import com.chenjing.apisecurity.util.SpringActiveUtils;
import com.chenjing.apisecurity.wrapper.HttpResponseWrapper;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 数据解密
 *
 * @author Chenjing
 * @date 2018/12/29
 */
@Slf4j
public class EncryptFilter implements Filter {

    private ApiProperties.EncryptProperties encryptProperties;

    private Encrypt encrypt;

    private SpringActiveUtils springActiveUtils;

    private ProductProvider productProvider;

    public EncryptFilter(ApiProperties apiProperties, Encrypt encrypt, SpringActiveUtils springActiveUtils,
                         ProductProvider productProvider) {
        this.encryptProperties = apiProperties.getEncrypt();
        this.encrypt = encrypt;
        this.springActiveUtils = springActiveUtils;
        this.productProvider = productProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("begin encrypt filter");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpResponseWrapper httpResponseWrapper = new HttpResponseWrapper(response);

        filterChain.doFilter(servletRequest, httpResponseWrapper);
        if (springActiveUtils.isNeedEncrypt(encryptProperties.getExcludeProfiles())) {
            String result = new String(httpResponseWrapper.getResponseData(), StandardCharsets.UTF_8);
            log.debug("not encrypt data = {}", result);
            String encryptStr;
            try {
                encryptStr = encrypt.encrypt(result, productProvider.getEncryptKey(req));
                log.debug("encrypt data = {}", encryptStr);
            } catch (Exception e) {
                // TODO: 2018/12/29
                throw new RuntimeException(e);
            }
            writeResponse(servletResponse, encryptStr);
        }
    }

    private void writeResponse(ServletResponse response, String responseString)
            throws IOException {
        @Cleanup PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
    }
}
