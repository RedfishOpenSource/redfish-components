package com.redfish.components.demo;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Paths;
import java.util.List;

/**
 * @author zly
 * @date 2024/6/14
 * @description TODO
 * @Copyright: 儒松科技
 */
@SpringBootApplication
@MapperScan("com.redfish.components.demo.mapper")
public class Application {

    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
//        IActRuExecutionService iActRuExecutionService = applicationContext.getBean(IActRuExecutionService.class);
//        List<ActRuExecution> actRuExecutions =  iActRuExecutionService.list();
//        System.out.println();

        String url = "jdbc:mysql://localhost:3306/rusong?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "zzw5730219";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("redfish")
                        .outputDir(Paths.get(System.getProperty("user.dir")+"/Demo") + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.redfish.components.demo")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude("ACT_RU_EXECUTION")
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .enableFileOverride()
                        .controllerBuilder()
                        .disable()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}