package com.redfish.components.common.juc.pool;


import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface ExecutorServiceEnhancer {


    static ExecutorService enhance(ExecutorService executorService){
        Map<String,ExecutorServiceEnhancer> enhancerMap = null;
        if (CollectionUtils.isEmpty(enhancerMap)){
            return executorService;
        }

        for (ExecutorServiceEnhancer enhancer:enhancerMap.values()){
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
