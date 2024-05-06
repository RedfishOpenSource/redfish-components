package com.redfish.components.common.eventpublisher;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.util.StringUtils;

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
 */
public class BaseDomainEvent<T> extends PayloadApplicationEvent<T> {


    /**
     * 事件类型。默认同步事件
     */
    private EventTypeEnum type = EventTypeEnum.LOCAL_SYNC_EVENT;

    /**
     * 幂等键。消息唯一标识。
     */
    private String idempotentKey;



    /**
     * 事件触发者（用于事件日志记录）。
     */
    private String creator;

    private BaseDomainEvent(Object source,T payLoad) {
        super(source,payLoad);
    }


    public EventTypeEnum getType() {
        return type;
    }

    public String getIdempotentKey() {
        return idempotentKey;
    }

    public String getCreator() {
        return creator;
    }

    public static class Builder<T> {
        private EventTypeEnum type;
        private String idempotentKey;
        private T payLoad;
        private String creator;
        private Object source;

        public Builder(Object source) {
            this.source = source;
        }

        public Builder<T> withType(EventTypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder<T> withIdempotentKey(String idempotentKey) {
            this.idempotentKey = idempotentKey;
            return this;
        }

        public Builder<T> withPayLoad(T payLoad) {
            this.payLoad = payLoad;
            return this;
        }

        public BaseDomainEvent<T> build() {
            if (null == this.type){
                this.type = EventTypeEnum.LOCAL_SYNC_EVENT;
            }
            if (this.idempotentKey == null){
                throw new RuntimeException("idempotentKey can not be null");
            }
            if (this.payLoad == null){
                throw new RuntimeException("payLoad can not be null");
            }
            if (!StringUtils.hasText(this.creator)){
                throw new RuntimeException("creator can not be null");
            }
            if (this.source == null){
                throw new RuntimeException("event source can not be null");
            }

            BaseDomainEvent event = new BaseDomainEvent(this.source,this.payLoad);
            event.type = this.type;
            event.idempotentKey = this.idempotentKey;
            event.creator = this.creator;
            return event;

        }
    }


}
