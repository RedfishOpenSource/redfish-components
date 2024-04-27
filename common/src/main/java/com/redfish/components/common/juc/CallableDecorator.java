package com.redfish.components.common.juc;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

/**
 * Callable上下文装饰器
 */
public interface CallableDecorator {

    static Callable decorate(Callable callable) {
        ServiceLoader<CallableDecorator> serviceLoader = ServiceLoader.load(CallableDecorator.class);
        Iterator<CallableDecorator> enhancerIterator = serviceLoader.iterator();

        while(enhancerIterator.hasNext()) {
            CallableDecorator enhancer = enhancerIterator.next();
            callable = enhancer.get(callable);
        }

        return callable;
    }

    Callable get(Callable callable);


}
