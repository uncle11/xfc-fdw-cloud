package com.xfc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-07-07 10:04
 */
@SpringBootApplication
@MapperScan({"com.xfc.dict.mapper"})
@EnableSwagger2
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class,args);
    }
}
