package com.rubber.wx.at.atp.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.wx.at.atp.dao.mapper")
@SpringBootApplication
public class AtAtpApiWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtAtpApiWebApplication.class, args);
    }

}
