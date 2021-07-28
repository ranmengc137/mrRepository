package com.tcc.demo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lw
 */
@SpringBootApplication
@MapperScan("com.tcc.demo.demo.mappers")
public class TccDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccDemoApplication.class, args);
        System.out.println("OK");
    }

}
