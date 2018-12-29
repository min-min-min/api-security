package com.chenjing.apisecurity;

import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 项目配置文件属性
 *
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

    /**
     * 解密属性
     */
    private DecryptProperties decrypt;

    /**
     * 加密属性
     */
    private EncryptProperties encrypt;

    @Data
    public static class HmacProperties {

        /**
         * 是否开启验签 默认true
         */
        private boolean enabled = true;

        /**
         * 产品标识名称，默认product_key
         */
        private String productUrlParamName = "product_key";

        /**
         * 对哪些url进行验签，默认/api/*
         */
        private List<String> urlPatterns = Lists.newArrayList("/api/*");

        /**
         * 从客户端的哪个头部参数获取客户端签名后的值，默认Authorization
         */
        private String headerName = HttpHeaders.AUTHORIZATION;

        /**
         * 排除激活spring profile不进行验签
         * e.g. 如果该值包含dev，那么激活application-dev.properties启动应用的时候 将不会验签
         */
        private List<String> excludeProfiles;
    }

    @Data
    public static class DecryptProperties {

        /**
         * 是否开启解密 默认true
         */
        private boolean enabled = false;

        /**
         * 对哪些uri进行解密，默认/api/*
         */
        private List<String> urlPatterns = Lists.newArrayList("/api/*");

        /**
         * 排除激活spring profile不进行解密
         * e.g. 如果该值包含dev，那么激活application-dev.properties启动应用的时候 将不会解密
         */
        private List<String> excludeProfiles;

    }

    @Data
    public static class EncryptProperties {

        /**
         * 是否开启解密 默认true
         */
        private boolean enabled = false;

        /**
         * 对哪些uri进行解密，默认/api/*
         */
        private List<String> urlPatterns = Lists.newArrayList("/api/*");

        /**
         * 排除激活spring profile不进行加密
         * e.g. 如果该值包含dev，那么激活application-dev.properties启动应用的时候 将不会加密
         */
        private List<String> excludeProfiles;

    }
}


