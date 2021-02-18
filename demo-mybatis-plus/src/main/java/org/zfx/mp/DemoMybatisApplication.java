package org.zfx.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.zfx.mp.mapper")
public class DemoMybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisApplication.class,args);
    }
}
