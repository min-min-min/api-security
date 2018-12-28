package com.chenjing.apisecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chenjing
 * @date 2018/12/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String productName;

    private String encryptKey;
}
