package com.redfish.components.demo;

import com.redfish.components.demo.repository.YourRepository;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class Application implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        applicationContext.getBean(YourRepository.class).yourDatabaseOperation();

    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}