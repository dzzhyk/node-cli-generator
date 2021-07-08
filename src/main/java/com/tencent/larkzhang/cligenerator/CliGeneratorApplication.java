package com.tencent.larkzhang.cligenerator;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author larkzhang
 */
@SpringBootApplication
public class CliGeneratorApplication {

    @Bean
    CliProperties cliProperties(){
        return new CliProperties();
    }

    @Bean
    VelocityEngine velocityEngine(){
        return new VelocityEngine();
    }

    public static void main(String[] args) {
        SpringApplication.run(CliGeneratorApplication.class, args);
    }

}