package com.redfish.components.common.juc;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Runable上下文装饰器
 */
public interface RunnableDecorator {

    static Runnable decorate(Runnable runnable) {
        ServiceLoader<RunnableDecorator> serviceLoader = ServiceLoader.load(RunnableDecorator.class);
        Iterator<RunnableDecorator> enhancerIterator = serviceLoader.iterator();

        while(enhancerIterator.hasNext()) {
            RunnableDecorator enhancer = enhancerIterator.next();
            runnable = enhancer.get(runnable);
        }

        return runnable;
    }

    Runnable get(Runnable runnable);


}
