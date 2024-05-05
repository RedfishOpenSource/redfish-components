package com.redfish.components.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.redfish.components.common.eventpublisher.CustomApplicationEventMulticaster;
import com.redfish.components.common.eventpublisher.CustomizableThreadFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ComponentScan("com.redfish.components.common")
@Configuration
public class CommonAutoconfiguration {


    @ConditionalOnMissingBean(name = CustomApplicationEventMulticaster.EVENT_MULTICASTER_THREAD_POOL)
    @Bean(CustomApplicationEventMulticaster.EVENT_MULTICASTER_THREAD_POOL)
    public ExecutorService eventMulticasterThreadPool(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,10,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(100),
                new CustomizableThreadFactory(CustomApplicationEventMulticaster.EVENT_MULTICASTER_THREAD_POOL),
                new ThreadPoolExecutor.CallerRunsPolicy());
        return TtlExecutors.getTtlExecutorService(threadPoolExecutor);
    }


}
