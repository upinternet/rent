package com.hyh.ease.rent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.hyh.ease.rent.dao")
@EnableCaching
public class RentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world  . bye.....");
            }
        }));

//        System.exit(0);
    }
}
