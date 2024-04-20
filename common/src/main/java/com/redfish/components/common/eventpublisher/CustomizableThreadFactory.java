package com.redfish.components.common.eventpublisher;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomizableThreadFactory implements ThreadFactory {

    private String threadNamePrefix;

    private ThreadGroup threadGroup;

    private int threadPriority = Thread.NORM_PRIORITY;

    private boolean daemon = false;


    private final AtomicInteger threadCount = new AtomicInteger();

    public CustomizableThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }


    public void setThreadGroupName(String name) {
        this.threadGroup = new ThreadGroup(name);
    }


    public void setThreadGroup(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
    }


    public ThreadGroup getThreadGroup() {
        return this.threadGroup;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }


    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }


    public boolean isDaemon() {
        return this.daemon;
    }

    protected String nextThreadName() {
        return getThreadNamePrefix() + this.threadCount.incrementAndGet();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(getThreadGroup(), runnable, nextThreadName());
        thread.setPriority(getThreadPriority());
        thread.setDaemon(isDaemon());
        return thread;
    }
}
