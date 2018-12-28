package com.chenjing.apisecurity.hmac;

import com.google.common.net.HttpHeaders;
import lombok.Data;

import java.util.List;

/**
 * hmac config properties
 *
 * @author Chenjing
 * @date 2018/12/27
 */
@Data
public class HmacProperties {

    /**
     * 产品标识名称
     */
    private String productUrlParamName = "product_key";

    /**
     * 对哪些url进行验签
     */
    private List<String> urlPatterns;

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
