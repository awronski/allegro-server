package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LoginScheduler {

    private final static Logger logger = LoggerFactory.getLogger(LoginScheduler.class);

    @Autowired
    private IAllegroClientFactory allegro;

    @PostConstruct
    public void init(){
        allegro
                .getAll()
                .stream()
                .filter(client -> !client.isLogged())
                .peek(client -> logger.debug("Do first login after start for {}", client.getClientId()))
                .forEach(IAllegroNiceApi::login);
    }

    @Scheduled(fixedDelay=19 * 60000)
    public void refreshSession() {
        logger.debug("Refreshing session");
        allegro
                .getAll()
                .forEach(IAllegroNiceApi::login);
    }

}
