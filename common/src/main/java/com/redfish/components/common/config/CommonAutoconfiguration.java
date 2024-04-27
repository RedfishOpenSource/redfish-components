package com.redfish.components.common.config;

import com.redfish.components.common.eventpublisher.CustomApplicationEventMulticaster;
import com.redfish.components.common.eventpublisher.CustomizableThreadFactory;
import com.redfish.components.juc.tp.enhancer.ThreadPoolBuilder;
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
        return ThreadPoolBuilder.create()
                .corePoolSize(10)
                .maximumPoolSize(10)
                .keepAliveTime(10)
                .unit(TimeUnit.SECONDS)
                .workQueue(new ArrayBlockingQueue<>(100))
                .threadFactory(new CustomizableThreadFactory(CustomApplicationEventMulticaster.EVENT_MULTICASTER_THREAD_POOL))
                .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }


}
