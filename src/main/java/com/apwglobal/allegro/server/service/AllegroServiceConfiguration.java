package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.conf.AllegroProperties;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllegroServiceConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(AllegroServiceConfiguration.class);

    @Autowired
    private AllegroProperties prop;

    @Bean
    public IAllegroNiceApi allegro() {

        logger.debug("Creating AllegroService, sandbox = {}", prop.isSandbox());

        com.apwglobal.nice.service.Configuration conf = new com.apwglobal.nice.service.Configuration(prop.getCountry());
        Credentials cred = new Credentials(prop.getUsername(), prop.getPassword(), prop.getKey());
        return new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .test(prop.isSandbox())
                .build();
    }

}
