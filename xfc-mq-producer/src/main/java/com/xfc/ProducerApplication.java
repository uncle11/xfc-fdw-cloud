package com.xfc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-05-04 14:06
 */
@SpringBootApplication
@MapperScan("com.xfc.producer.mapper")
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }

}
