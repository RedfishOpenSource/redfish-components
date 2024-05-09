package com.redfish.components.demo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(Main.class,args);
        GraphQL graphQL = applicationContext.getBean(GraphQL.class);
        String query = "{getUser(userId:100){userId,userName,age}}";
        ExecutionResult execute = graphQL.execute(query);
        System.out.println(execute.toSpecification());


    }

}
