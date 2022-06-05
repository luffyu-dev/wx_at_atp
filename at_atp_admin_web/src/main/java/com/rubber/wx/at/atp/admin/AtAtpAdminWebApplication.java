package com.rubber.wx.at.atp.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.wx.at.atp.dao.mapper")
@SpringBootApplication
public class AtAtpAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtAtpAdminWebApplication.class, args);
    }

}
