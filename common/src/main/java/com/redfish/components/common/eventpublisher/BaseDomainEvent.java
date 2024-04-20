package com.redfish.components.common.eventpublisher;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 发生的一件事，是既成事实，是不可逆的
 * 案例一，OrderCanceledEvent，被服务端发布出来后，代表某个订单已被取消了。
 * 对这个事件感兴趣的人或系统可以基于这个事实做出响应。
 * 案例二，数据同步可以通过发布Event来完成。
 *
 * 大多数情况下发布BaseDomainEvent即可，如果有特定场景，自定义Event继承BaseDomainEvent。
 *
 * 相关属性仅提供get方法，事件一旦通过Builder创建后，即不可更改。
 *
 * @param <T>
 */
public class BaseDomainEvent<T> implements Serializable {


    /**
     * 事件类型
     */
    private EventTypeEnum type;

    /**
     * 幂等键。消息唯一标识。
     */
    private String idempotentKey;

    /**
     * 事件信息数据。
     */
    private T data;

    /**
     * 领域事件的创建时间（用于事件日志记录）。
     */
    private LocalDateTime createAt;

    /**
     * 事件触发者（用于事件日志记录）。
     */
    private String creator;


    public EventTypeEnum getType() {
        return type;
    }

    public String getIdempotentKey() {
        return idempotentKey;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getCreator() {
        return creator;
    }
}
