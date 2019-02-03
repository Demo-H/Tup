package com.tupperware.biz.utils;


import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by dhunter on 2018/10/24.
 */

public class DesUtils {

    public static final String  KEY = "IgEt168t";

    /**
     * DES加密
     * @param content
     * @return
     */
//    public static String encrypt(String content) throws Exception{
//        SecureRandom sr = new SecureRandom();
//        Cipher cipher = Cipher.getInstance("DES");// 创建密码器
//        byte[] byteContent = content.getBytes("utf-8");
//
//        DESKeySpec desKey = new DESKeySpec(KEY.getBytes());
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey securekey = keyFactory.generateSecret(desKey);
//
//        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);// 初始化为加密模式的密码器
//
//        byte[] result = cipher.doFinal(byteContent);// 加密
//        return Base64.encodeBase64String(result);//通过Base64转码返回
//    }

    /**
     * 解密
     * @param src byte[]
     * @return byte[]
     * @throws Exception
     */

    public static String decrypt(String src) throws Exception {
        byte[] bytes = Base64.decode(src.getBytes("UTF-8"), Base64.DEFAULT);
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(KEY.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
//        cipher.doFinal(bytes);
        String string = new String(cipher.doFinal(bytes));
        return string;
    }
}
