package com.chenjing.apisecurity;

import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Chenjing
 * @date 2018/12/27
 */
@ConfigurationProperties(prefix = "api")
@Data
public class ApiProperties {

    /**
     * hmac验签属性
     */
    private HmacProperties hmac;

    @Data
    public static class HmacProperties {

        /**
         * 是否开启验签
         */
        private boolean enabled = true;

        /**
         * 产品标识名称
         */
        private String productUrlParamName = "product_key";

        /**
         * 对哪些url进行验签
         */
        private List<String> urlPatterns = Lists.newArrayList("/api/*");

        /**
         * 从客户端的哪个头部参数获取客户端签名后的值
         */
        private String headerName = HttpHeaders.AUTHORIZATION;

        /**
         * 排除激活spring profile不进行验签
         * e.g. 如果该值包含dev，那么激活application-dev.properties启动应用的时候 将不会验签
         */
        private List<String> excludeProfiles;
    }
}


