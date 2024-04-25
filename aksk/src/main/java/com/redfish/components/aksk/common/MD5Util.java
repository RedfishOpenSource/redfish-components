package com.redfish.components.aksk.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String md5(String s) {
        return bytesToHex(md5ToBytes(s));
    }

    private static byte[] md5ToBytes(String data) {
        try {
            // 步骤 1: 创建 MessageDigest 对象，使用 SHA-256 算法
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 步骤 2: 使用 update() 方法传入数据
            md.update(data.getBytes());

            // 步骤 3: 调用 digest() 方法计算哈希值
            byte[] hashValue = md.digest();

            // 输出哈希值
            return hashValue;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }

}
