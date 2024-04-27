package com.redfish.components.common.juc;

import com.redfish.components.juc.tp.enhancer.ThreadLocalTransfer;

import java.util.HashMap;
import java.util.Map;

public class ContextTransferImpl extends ThreadLocalTransfer {

    @Override
    public Map<ThreadLocal, Object> collectThreadLocal() {
        Map<ThreadLocal, Object> threadLocalObjectMap = new HashMap<>();
        threadLocalObjectMap.put(Demo.context,Demo.context.get());
        return threadLocalObjectMap;
    }
}
