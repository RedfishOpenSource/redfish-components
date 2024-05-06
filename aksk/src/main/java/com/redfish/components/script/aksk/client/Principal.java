package com.redfish.components.script.aksk.client;

import java.io.Serializable;

public class Principal implements Serializable {

    /**
     * 密钥
     */
    private String appKey;

    /**
     * 签名
     */
    private String signature;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
