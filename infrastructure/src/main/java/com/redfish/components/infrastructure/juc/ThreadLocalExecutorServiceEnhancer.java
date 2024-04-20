package com.redfish.components.infrastructure.juc;




import com.redfish.components.common.juc.ExecutorServiceEnhancer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;


public class ThreadLocalExecutorServiceEnhancer implements ExecutorServiceEnhancer {

    @Override
    public ExecutorService get(ExecutorService executorService){
        if (executorService == null || executorService instanceof ExecutorServiceProxy) {
            return executorService;
        }
        return new ExecutorServiceProxy(executorService);
    }


    private static class ExecutorServiceProxy implements ExecutorService {

        private ExecutorService target;

        public ExecutorServiceProxy(ExecutorService target) {
            this.target = target;
        }

        @Override
        public void execute(Runnable command) {
            command = RunnableDecorator.decorate(command);
            target.execute(command);
        }

        @Override
        public Future<?> submit(Runnable task) {
            task = RunnableDecorator.decorate(task);
            return target.submit(task);
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            task = RunnableDecorator.decorate(task);
            return target.submit(task,result);
        }


        @Override
        public <T> Future<T> submit(Callable<T> task) {
            task = CallableDecorator.decorate(task);
            return target.submit(task);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            throw new RuntimeException("暂未实现");
        }


        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            throw new RuntimeException("暂未实现");
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            throw new RuntimeException("暂未实现");
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            throw new RuntimeException("暂未实现");
        }


        @Override
        public void shutdown() {
            target.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return target.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return target.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return target.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return target.awaitTermination(timeout,unit);
        }


    }


}
