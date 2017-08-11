package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RestApiScheduler {

    private final static Logger logger = LoggerFactory.getLogger(RestApiScheduler.class);
    private final IAllegroClientFactory allegro;

    @Autowired
    public RestApiScheduler(IAllegroClientFactory allegro) {
        this.allegro = allegro;
    }

    @Scheduled(fixedDelay=60 * 60000)
    public void refreshTokens() {
        logger.debug("Starting refreshTokens");
        allegro
                .getAll()
                .forEach(this::refreshToken);
    }

    private void refreshToken(IAllegroNiceApi client) {
        if (client.getRestApiSession() != null) {
            client.refreshRestApiSession();
            logger.debug("Token refreshed for client {}", client.getClientId());
        }
    }

}
