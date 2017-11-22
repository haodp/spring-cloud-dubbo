package com.lakala;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@MapperScan("com.lakala.dao.*.mapper")
public class PpayMgrApplication extends SpringServletContainerInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PpayMgrApplication.class, args);
    }
}
