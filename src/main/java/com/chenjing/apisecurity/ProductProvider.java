package com.chenjing.apisecurity;


/**
 * @author Chenjing
 * @date 2018/12/28
 */
public interface ProductProvider {

    /**
     * 获取对应的产品信息
     * 用户必须要实现
     *
     * @param key url参数名={@code HmacProperties.productKeyName}的值
     * @return 产品信息
     * @see ApiProperties.HmacProperties
     */
    Product getProduct(String key);
}
