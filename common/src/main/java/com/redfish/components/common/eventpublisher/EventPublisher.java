package com.redfish.components.common.eventpublisher;



import com.redfish.components.common.juc.ThreadPoolBuilder;

import java.util.concurrent.*;

public abstract class EventPublisher {

    final void publish(BaseDomainEvent event){

        EventTypeEnum eventType = event.getType();

        switch (eventType){
            case LOCAL_SYNC_EVENT:
                publishLocalSyncEvent(event);
                break;
            case LOCAL_ASYNC_EVENT:
                publishLocalAsyncEvent(event);
                break;
            case MQ_EVENT:
                publishMQEvent(event);
                break;
            default:
        }
    }

    protected abstract void publishLocalSyncEvent(BaseDomainEvent event);

    /**
     * 1，多Listener处理同一个event。
     * 造成的问题
     * 如果同一个异步event对应多个listener，则多个listener为并行执行。
     * 如，event1对应listener1和listener2，都为并行处理，都会根据id查询user表，并且更新user表。
     * 业务上应该是两个更新都生效，但是因为listener1和listener2是并行的，所以可能存在一个listener的更新结果被另一个listener更新覆盖的情况。
     *
     * 方式1：应避免多个listener处理同一个领域的事件。
     * 方式2，通过加锁(乐观锁，悲观锁)控制多个Listener顺序处理。
     *
     * 2，发送事件的地方开启事务，无需额外处理。
     * 3，发送事件的地方没有开启事务，则在发送事件的代码结构上控制顺序。
     * 方式1：通过切面的方式，切面中，join.process后发送事件。
     *
     * @param event
     */
    protected abstract void publishLocalAsyncEvent(BaseDomainEvent event);

    /**
     * todo 针对MQ的特性，还要进一步设计封装。或者MQ单独封装。
     * 1，顺序队列 or 非顺序队列。
     * 2，集群消费还是广播消费。
     * 3，事务消息。
     *
     * @param event
     */
    abstract public void publishMQEvent(BaseDomainEvent event);

}
