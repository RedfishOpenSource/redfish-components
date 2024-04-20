package com.redfish.components.infrastructure.distributionlock;


import com.redfish.components.common.distributionlock.DistributeLockGetter;
import java.util.concurrent.locks.Lock;

/**
 * 基于Redission的包装
 */
public class RedissionDistributeLockGetter implements DistributeLockGetter {


    @Override
    public Lock getLock(String resourceTag){
        return null;
    }

}
