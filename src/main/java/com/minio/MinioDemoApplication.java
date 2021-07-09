package com.minio;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

import tk.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwaggerBootstrapUI
@SpringBootApplication
@MapperScan("com.minio.dao")
public class MinioDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioDemoApplication.class, args);
    }

}
