
package com.redfish.components.common.juc;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public class ParallelsUtils {

    /**
     * 并行执行function，等待所有任务执行完毕
     *
     * @param inputs
     * @param function
     * @param executorService
     * @return
     * @param <E>
     * @param <R>
     */
    public static <E,R> Map<E,R> functionBlock(List<E> inputs, Function<E,R> function, ExecutorService executorService){

        Map<E,CompletableFuture<R>> completableFutureMap = function(inputs,function,executorService);
        Collection<CompletableFuture<R>> completableFutures = completableFutureMap.values();


        if (CollectionUtils.isEmpty(completableFutureMap)){
            return new HashMap<>();
        }

        try {
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Map<E,R> result = new HashMap<>();
        for (E e:completableFutureMap.keySet()){
            CompletableFuture<R> completableFuture = completableFutureMap.get(e);
            if (null == completableFuture){
                continue;
            }
            try {
                result.put(e,completableFuture.get());
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }

        }
        return result;
    }


    /**
     * 并行执行function，不用等待所有任务执行完毕
     *
     * @param inputs
     * @param function
     * @param executorService
     * @return
     * @param <E>
     * @param <R>
     */
    public static <E,R> Map<E,CompletableFuture<R>> function(List<E> inputs, Function<E,R> function, ExecutorService executorService){
        if (CollectionUtils.isEmpty(inputs)){
            return null;
        }

        Map<E,CompletableFuture<R>> result = new ConcurrentHashMap<>();
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (E e:inputs){
            CompletableFuture<R> completableFuture = CompletableFuture.supplyAsync(() -> function.apply(e), executorService);
            completableFutures.add(completableFuture);
            result.put(e,completableFuture);
        }

        return result;
    }


    /**
     * 并行消费，无需等待结果。
     *
     * @param inputs
     * @param consumer
     * @param executorService
     * @param <E>
     */
    public static <E> List<CompletableFuture> consume(List<E> inputs, Consumer<E> consumer, ExecutorService executorService){
        if (CollectionUtils.isEmpty(inputs)){
            return null;
        }

        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (E e:inputs){
            CompletableFuture completableFuture = CompletableFuture.runAsync(()->consumer.accept(e),executorService);
            completableFutures.add(completableFuture);
        }

        return completableFutures;
    }

    /**
     * 并行消费。所有inputs都被消费完成后，才继续执行下一步
     *
     * @param inputs
     * @param consumer
     * @param executorService
     * @param <E>
     */
    public static <E> void consumeBlock(List<E> inputs, Consumer<E> consumer, ExecutorService executorService){
        List<CompletableFuture> completableFutures = consume(inputs,consumer,executorService);
        if (CollectionUtils.isEmpty(completableFutures)){
            return;
        }

        try {
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
