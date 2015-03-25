package com.apwglobal.allegro.server.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

    @Bean
    public AllegroExceptionHandler allegroExceptionHandler() {
        return new AllegroExceptionHandler();
    }

}
