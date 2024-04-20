package com.redfish.components.infrastructure.eventpublisher.impl;


import com.redfish.components.common.eventpublisher.BaseDomainEvent;
import com.redfish.components.common.eventpublisher.EventPublisher;

public class EvenPublisherImpl extends EventPublisher {


    @Override
    protected void publishLocalSyncEvent(BaseDomainEvent event) {
        // 待实现
    }

    @Override
    public void publishMQEvent(BaseDomainEvent event) {
        // 待实现
    }


}
