package com.redfish.components.common.eventpublisher;



import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@ConditionalOnMissingBean(EventPublisher.class)
@Component
public class EvenPublisherImpl extends EventPublisher implements ApplicationEventPublisherAware {


    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    protected void publishLocalSyncEvent(BaseDomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }


    @Override
    protected void publishLocalAsyncEvent(BaseDomainEvent event) {

        if (EventTypeEnum.LOCAL_ASYNC_EVENT != event.getType()){
            throw new RuntimeException("event type not match");
        }

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    applicationEventPublisher.publishEvent(event);
                }
            });
        } else {
            applicationEventPublisher.publishEvent(event);
        }
    }

    @Override
    public void publishMQEvent(BaseDomainEvent event) {
        // 待实现
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
