package com.redfish.components.common.juc;

import java.util.*;

public abstract class ThreadLocalTransfer {

    static Map<ThreadLocal,Object> getAllContext() {
        Map<ThreadLocal,Object> allThreadLocalMap = new HashMap<>();

        ServiceLoader<ThreadLocalTransfer> serviceLoader = ServiceLoader.load(ThreadLocalTransfer.class);
        Iterator<ThreadLocalTransfer> enhancerIterator = serviceLoader.iterator();

        while(enhancerIterator.hasNext()) {
            ThreadLocalTransfer enhancer = enhancerIterator.next();
            Map<ThreadLocal,Object> curThreadLocalMap = enhancer.collectThreadLocal();
            curThreadLocalMap = Optional.ofNullable(curThreadLocalMap).orElse(new HashMap<>());

            allThreadLocalMap.putAll(curThreadLocalMap);
        }

        return allThreadLocalMap;
    }

    /**
     * 异步线程ThreadLocal初始化
     */
    static void initContext(Map<ThreadLocal,Object> allContext){
        allContext.forEach((threadLocal,object)->{
            threadLocal.set(object);
        });
    }

    /**
     * 异步线程ThreadLocal变量clear
     */
    static void clearContext(Map<ThreadLocal,Object> allContext) {
        allContext.forEach((threadLocal,object)->{
            threadLocal.remove();
        });
    }


    /**
     * 收集ThreadLocal对象。
     * 用于将父线程中的ThreadLocal和对应的值传递给子线程
     *
     * @return
     */
    public Map<ThreadLocal, Object> collectThreadLocal() {
        return new HashMap<>();
    }




}
