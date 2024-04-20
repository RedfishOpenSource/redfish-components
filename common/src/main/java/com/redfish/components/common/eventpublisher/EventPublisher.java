package com.redfish.components.common.eventpublisher;



import com.redfish.components.common.juc.ThreadPoolBuilder;

import java.util.concurrent.*;

public abstract class EventPublisher {

    private final ExecutorService EVENT_THREAD_POOL = ThreadPoolBuilder.create()
            .corePoolSize(3)
            .maximumPoolSize(5)
            .keepAliveTime(5)
            .unit(TimeUnit.HOURS)
            .workQueue(new SynchronousQueue<>())
            .threadFactory(new CustomizableThreadFactory("EVENT-PUBLISHER-POOL-%d"))
            .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
            .build();

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

    final public void publishLocalAsyncEvent(BaseDomainEvent event){
        EVENT_THREAD_POOL.execute(()->{
            publishLocalSyncEvent(event);
        });
    }

    abstract public void publishMQEvent(BaseDomainEvent event);

}
