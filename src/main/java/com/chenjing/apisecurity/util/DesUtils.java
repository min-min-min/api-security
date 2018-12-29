package com.chenjing.apisecurity.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;

public abstract class DesUtils {

    private static final String DES = "DES";

    public static String encrypt(String data, String key) throws Exception {

        Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64URLSafeString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decrypt(String data, String key) throws Exception {
        Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
        return new String(cipher.doFinal(Base64.decodeBase64(data)), StandardCharsets.UTF_8);
    }

    private static Cipher getCipher(String key, int decryptMode) throws Exception {
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(decryptMode, secretKey);
        return cipher;
    }

}
