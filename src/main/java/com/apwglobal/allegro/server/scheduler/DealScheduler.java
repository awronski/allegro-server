package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DealScheduler {

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay=7 * 60000)
    @Transactional
    public void syncDeals() {
        allegro
                .getAll()
                .forEach(this::syncDealsForGivenClient);
    }

    private void syncDealsForGivenClient(IAllegroNiceApi client) {
        Optional<Long> lastEventId = dealService.findLastRowId(client.getClientId());

        client
                .login()
                .getDeals(lastEventId.orElse(0l))
                .forEach(dealService::saveDeal);
    }

}
