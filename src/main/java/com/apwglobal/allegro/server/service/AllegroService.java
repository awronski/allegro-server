package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllegroService {

    @Value("${alegro.username}")
    private String username;

    @Value("${alegro.password}")
    private String password;

    @Value("${alegro.country}")
    private int country;

    @Value("${alegro.key}")
    private String key;

    @Value("${alegro.localVersion}")
    private long localVersion;

    @Bean
    public IAllegroNiceApi allegro() {
        com.apwglobal.nice.service.Configuration conf = new com.apwglobal.nice.service.Configuration(country);
        Credentials cred = new Credentials(username, password, key);
        return new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .build();
    }


}
