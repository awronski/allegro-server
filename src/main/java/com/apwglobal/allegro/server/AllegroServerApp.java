package com.apwglobal.allegro.server;

import com.apwglobal.allegro.server.aop.AllegroExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
public class AllegroServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AllegroServerApp.class, args);
    }

    @Bean
    public AllegroExceptionHandler allegroExceptionHandler() {
        return new AllegroExceptionHandler();
    }

}
