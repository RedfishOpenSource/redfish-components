package com.redfish.components.script.demo;

import org.junit.Test;

import javax.script.*;

public class CompiledScriptMultiThreadTest {

    @Test
    public void unSafeTest() throws ScriptException, InterruptedException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // 编译脚本
        CompiledScript compiledScript = ((Compilable) engine).compile("counter += 1;");

        // 假设counter是一个共享变量，这里简化处理，实际上应确保每个线程有自己的作用域
        final int threadCount = 100;
        final int iterations = 1000;
        Thread[] threads = new Thread[threadCount];

        // 初始化共享变量
        final double[] counter = {0};
        engine.put("counter", counter[0]);
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    try {
                        synchronized (compiledScript){
                            compiledScript.eval();
                        }

                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected counter: " + (threadCount * iterations));
        System.out.println("Actual counter: " + (Double) engine.get("counter"));

    }

}
