package com.example.liteflowdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//把你定义的组件扫入Spring上下文中
public class LiteflowDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteflowDemoApplication.class, args);
    }

}
