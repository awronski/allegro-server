package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllegroServiceConfiguration {

    @Value("${allegro.username}")
    private String username;

    @Value("${allegro.password}")
    private String password;

    @Value("${allegro.country}")
    private int country;

    @Value("${allegro.key}")
    private String key;

    @Value("${allegro.sandbox}")
    private Boolean test;

    @Bean
    public IAllegroNiceApi allegro() {
        com.apwglobal.nice.service.Configuration conf = new com.apwglobal.nice.service.Configuration(country);
        Credentials cred = new Credentials(username, password, key);
        return new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .test(test)
                .build();
    }

}
