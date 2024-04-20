package com.redfish.components.common.juc;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;

public interface ExecutorServiceEnhancer {


    static ExecutorService enhance(ExecutorService executorService){
        ServiceLoader<ExecutorServiceEnhancer> serviceLoader = ServiceLoader.load(ExecutorServiceEnhancer.class);
        Iterator<ExecutorServiceEnhancer> enhancerIterator = serviceLoader.iterator();

        while(enhancerIterator.hasNext()) {
            ExecutorServiceEnhancer enhancer = enhancerIterator.next();
            executorService = enhancer.get(executorService);
        }

        return executorService;
    }



    /**
     * 对ExecutorService进行增强
     *
     * @param executorService
     * @return
     */
    ExecutorService get(ExecutorService executorService);


}
