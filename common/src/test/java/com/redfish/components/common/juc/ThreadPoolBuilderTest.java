package com.redfish.components.common.juc;

import com.redfish.components.common.eventpublisher.CustomizableThreadFactory;
import com.redfish.components.juc.tp.enhancer.ThreadPoolBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolBuilderTest {



    @Test
    public void simpleTest() throws InterruptedException {

        Demo.context.set("contentTest");
        System.out.println("主线程输出:" + Demo.context.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程输出:" + Demo.context.get());
            }
        }).start();

        ExecutorService executorService = ThreadPoolBuilder.create()
                .corePoolSize(2)
                .maximumPoolSize(2)
                .unit(TimeUnit.SECONDS)
                .keepAliveTime(1)
                .workQueue(new LinkedBlockingQueue<Runnable>(1))
                .threadFactory(new CustomizableThreadFactory("线程池"))
                .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池子线程输出:" + Demo.context.get());
            }
        });
        Thread.sleep(2000);
        System.out.println("主线程输出:" + Demo.context.get());

    }

}
