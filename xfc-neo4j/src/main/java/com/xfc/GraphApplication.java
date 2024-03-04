package com.xfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:52
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class GraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphApplication.class,args);
    }
}
