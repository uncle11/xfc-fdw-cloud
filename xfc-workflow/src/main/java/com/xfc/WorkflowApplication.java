package com.xfc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-06-16 14:53
 */
@SpringBootApplication
@MapperScan({"com.xfc.workflow.mapper"})
public class WorkflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class,args);
    }
}
