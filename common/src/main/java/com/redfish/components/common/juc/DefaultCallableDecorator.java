package com.redfish.components.common.juc;

import java.util.Map;
import java.util.concurrent.Callable;

public class DefaultCallableDecorator implements CallableDecorator{

    @Override
    public Callable get(Callable callable) {
        if (callable == null || callable instanceof CallableeProxy) {
            return callable;
        }

        return new CallableeProxy(callable);
    }

    private static class CallableeProxy<V> implements Callable<V> {

        private Callable<V> target;

        private final long MAIN_THREAD_ID = Thread.currentThread().getId();

        // region 传递给异步线程的ThreadLocal对象
        Map<ThreadLocal,Object> allThreadLocalMap = ThreadLocalTransfer.getAllContext();

        // endregion

        public CallableeProxy(Callable<V> target) {
            this.target = target;
        }




        /**
         * 异步线程ThreadLocal初始化
         */
        private void initContext() {
            ThreadLocalTransfer.initContext(allThreadLocalMap);
        }

        /**
         * 异步线程ThreadLocal变量clear
         */
        private void clearContext() {
            ThreadLocalTransfer.clearContext(allThreadLocalMap);
        }

        @Override
        public V call() throws Exception {
            try{
                initContext();
                return target.call();
            } finally {
                // 当thread非父线程时，执行clear。避免new ThreadPoolExecutor.CallerRunsPolicy()导致父线程context丢失
                if (MAIN_THREAD_ID != Thread.currentThread().getId()){
                    clearContext();
                }
            }

        }



    }

}
