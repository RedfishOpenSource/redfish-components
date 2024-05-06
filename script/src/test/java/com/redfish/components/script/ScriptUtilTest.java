package com.redfish.components.script;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ScriptUtilTest {

    @Test
    public void simpleTest() throws InterruptedException {
        final int threadCount = 100;
        final int iterations = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    Map<String, Object> context = new HashMap<>();
                    context.put("counter",Integer.valueOf(100));
                    Object result = ScriptUtil.runScript(ScriptTypeEnum.JAVA_SCRIPT,"counter += 1;",context);
                    System.out.println(result);
                }
            });
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread t : threads) {
            t.join();
        }



    }

}
