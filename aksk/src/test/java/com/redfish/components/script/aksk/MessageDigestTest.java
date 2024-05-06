package com.redfish.components.script.aksk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {

    @Test
    public void test() {

        String data = "Hello, MessageDigest!";

        try {
            // 步骤 1: 创建 MessageDigest 对象，使用 SHA-256 算法
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 步骤 2: 使用 update() 方法传入数据
            md.update(data.getBytes());

            // 步骤 3: 调用 digest() 方法计算哈希值
            byte[] hashValue = md.digest();

            // 输出哈希值
            System.out.println("Hash Value (hex): " + bytesToHex(hashValue));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available.");
            e.printStackTrace();
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
