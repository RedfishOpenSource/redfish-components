package com.redfish.components.common.eventpublisher;

import com.redfish.components.common.juc.ThreadPoolBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Component(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
public class CustomApplicationEventMulticaster extends SimpleApplicationEventMulticaster implements ApplicationContextAware {

    public static final String EVENT_MULTICASTER_THREAD_POOL = "eventMulticasterThreadPool";

    public CustomApplicationEventMulticaster(@Qualifier(EVENT_MULTICASTER_THREAD_POOL) ExecutorService executorService) {
        setTaskExecutor(executorService);
    }

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
        Executor executor = getTaskExecutor();

        boolean syncEventFlag = false;
        if (event instanceof BaseDomainEvent){
            BaseDomainEvent baseDomainEvent = (BaseDomainEvent)event;
            if (baseDomainEvent.getType() == EventTypeEnum.LOCAL_ASYNC_EVENT){
                syncEventFlag = true;
            }
        }


        for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            if (syncEventFlag && executor != null) {
                executor.execute(() -> invokeListener(listener, event));
            }
            else {
                invokeListener(listener, event);
            }
        }
    }

    private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
        return ResolvableType.forInstance(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setBeanFactory(applicationContext);
    }
}
