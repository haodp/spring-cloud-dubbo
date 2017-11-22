package com.lakala.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis加载
 *
 * @author user
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MapperConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {

        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.lakala.dao.**");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

}
