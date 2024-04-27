package com.redfish.components.common.juc;

import java.util.Map;

public class DefaultRunnableDecorator implements RunnableDecorator{

    public Runnable get(Runnable runnable) {
        if (runnable == null || runnable instanceof DefaultRunnableDecorator) {
            return runnable;
        }

        return new RunableProxy(runnable);
    }


    private static class RunableProxy implements Runnable{

        private Runnable target;

        private final long MAIN_THREAD_ID = Thread.currentThread().getId();

        // region 传递给异步线程的ThreadLocal对象
        Map<ThreadLocal,Object> allThreadLocalMap = ThreadLocalTransfer.getAllContext();

        // endregion

        public RunableProxy(Runnable target) {
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
        public void run() {
            try{
                initContext();
                target.run();
            }finally {
                // 当thread非父线程时，执行clear。避免new ThreadPoolExecutor.CallerRunsPolicy()导致父线程context丢失
                if (MAIN_THREAD_ID != Thread.currentThread().getId()){
                    clearContext();
                }
            }

        }



    }

}
