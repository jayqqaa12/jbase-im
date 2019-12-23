package com.jayqqaa12.im.gateway;

import com.jayqqaa12.jbase.spring.boot.EnableBasic;
import com.jayqqaa12.jbase.spring.boot.EnableDb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@EnableBasic
@EnableDb
@SpringBootApplication
@MapperScan("com.jayqqaa12.im.*.mapper")
public class IMApplication {

    public static void main(String[] args)   {

        new SpringApplicationBuilder(IMApplication.class).web(WebApplicationType.NONE).run(args);
    }


}
