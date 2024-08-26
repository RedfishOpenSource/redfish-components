package com.redfish.components.common.juc.pool;

import java.util.concurrent.Callable;

/**
 * Callable上下文装饰器
 */
public class CallableDecorator {

    public static Callable decorate(Callable callable) {
        if (callable == null || callable instanceof CallableeProxy) {
            return callable;
        }

        return new CallableeProxy(callable);
    }

    private static class CallableeProxy<V> implements Callable<V> {

        private Callable<V> target;

        private final long MAIN_THREAD_ID = Thread.currentThread().getId();

        // region 传递给异步线程的ThreadLocal对象

        // region 传递给异步线程的ThreadLocal对象


        // endregion

        public CallableeProxy(Callable<V> target) {
            this.target = target;

            // 对传递的ThreadLocal对象初始化
            initThreadLocalVariable();
        }

        private void initThreadLocalVariable() {

        }


        /**
         * 异步线程ThreadLocal初始化
         */
        private void initContext() {

        }

        /**
         * 异步线程ThreadLocal变量clear
         */
        private void clearContext() {

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
