package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DealScheduler {

    private final static Logger logger = LoggerFactory.getLogger(DealScheduler.class);

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay=7 * 60000)
    @Transactional
    public void syncDeals() {
        logger.debug("Starting syncDeals");
        allegro
                .getAll()
                .forEach(this::syncDealsForGivenClient);
    }

    private void syncDealsForGivenClient(IAllegroNiceApi client) {
        Optional<Long> lastEventId = dealService.findLastRowId(client.getClientId());

        client
                .getDeals(lastEventId.orElse(0L))
                .forEach(dealService::saveDeal);
    }

}
