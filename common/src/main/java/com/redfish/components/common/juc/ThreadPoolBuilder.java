package com.redfish.components.common.juc;

import java.util.concurrent.*;

/**
 * 线程池Builder
 *
 * 不同的线程池维护在各自的模块中。
 * 项目中涉及到线程就需要考虑ThreadLocal传递问题，因此通过该ThreadPoolBuilder统一创建ThreadPool，统一解决ThreadLocal传递和remove问题。
 */
public class ThreadPoolBuilder {

    private int corePoolSize;

    private int maximumPoolSize;

    private long keepAliveTime;

    private TimeUnit unit;

    private BlockingQueue<Runnable> workQueue;

    private ThreadFactory threadFactory;

    private RejectedExecutionHandler rejectedExecutionHandler;

    private ThreadPoolBuilder() {
    }

    public static ThreadPoolBuilder create(){
        return new ThreadPoolBuilder();
    }

    public ThreadPoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ThreadPoolBuilder maximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public ThreadPoolBuilder unit(TimeUnit unit) {
        this.unit = unit;
        return this;
    }

    public ThreadPoolBuilder workQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    public ThreadPoolBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public ThreadPoolBuilder rejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        return this;
    }

    public ExecutorService build(){
        if (this.rejectedExecutionHandler == null){
            this.rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        }
        if (this.workQueue == null){
            this.workQueue = new LinkedBlockingQueue<>();
        }

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(this.corePoolSize,this.maximumPoolSize,this.keepAliveTime, this.unit,
                this.workQueue,this.threadFactory,this.rejectedExecutionHandler);

        ExecutorService executorService = ExecutorServiceEnhancer.enhance(threadPool);
        return executorService;
    }


}
