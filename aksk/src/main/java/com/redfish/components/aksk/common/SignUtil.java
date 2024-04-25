package com.redfish.components.aksk.common;

public class SignUtil {

    public static String sign(String appSecret, String content) {
        if (appSecret == null || appSecret.length() == 0){
            throw new RuntimeException("appSecret can not be empty.");
        }
        if (content == null || content.length() == 0){
            throw new RuntimeException("content can not be empty.");
        }
        String sign = MD5Util.md5(appSecret + content);
        return sign;
    }

}
