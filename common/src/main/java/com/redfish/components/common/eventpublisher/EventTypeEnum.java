package com.redfish.components.common.eventpublisher;

public enum EventTypeEnum {

    /**
     * 本地同步事件，如Spring-Event。
     */
    LOCAL_SYNC_EVENT("localSyncEvent","本地事件"),
    /**
     * 本地异步事件，如，对Spring-Event封装。
     */
    LOCAL_ASYNC_EVENT("lcoalAsyncEvent","本地异步事件"),
    /**
     * 服务间事件，如MQ，EventBridge等中间件产品。
     */
    MQ_EVENT("mqEvent","MQ事件");


    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;


    EventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
