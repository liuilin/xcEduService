package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Daniel Liu 2019/12/12 19:22
 */
@SpringBootApplication
@EntityScan( basePackages = {"com.xuecheng.framework.domain"} )
@ComponentScan( basePackages = {"com.xuecheng.framework"} )
@ComponentScan( basePackages = "com.xuecheng.manage_cms_client" )
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class, args);
    }
}