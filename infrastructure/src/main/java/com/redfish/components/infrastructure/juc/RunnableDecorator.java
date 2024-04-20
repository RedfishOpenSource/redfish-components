package com.redfish.components.infrastructure.juc;

/**
 * Runable上下文装饰器
 */
public class RunnableDecorator {

    public static Runnable decorate(Runnable runnable) {
        if (runnable == null || runnable instanceof RunableProxy) {
            return runnable;
        }

        return new RunableProxy(runnable);
    }


    private static class RunableProxy implements Runnable{

        private Runnable target;

        private final long MAIN_THREAD_ID = Thread.currentThread().getId();

        // region 传递给异步线程的ThreadLocal对象


        // endregion

        public RunableProxy(Runnable target) {
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
