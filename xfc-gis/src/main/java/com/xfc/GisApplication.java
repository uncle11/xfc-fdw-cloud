package com.xfc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-22 9:56
 */
@MapperScan({"com.xfc.gis.mapper"})
@SpringBootApplication
public class GisApplication {
    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class,args);
    }
}
