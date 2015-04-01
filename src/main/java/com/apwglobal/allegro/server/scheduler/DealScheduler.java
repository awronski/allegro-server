package com.apwglobal.allegro.server.scheduler;

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
    private IAllegroNiceApi allegro;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay=7 * 60000)
    @Transactional
    public void syncDeals() {
        Optional<Long> lastEventId = dealService.findLastRowId();

        allegro
                .login()
                .getDeals(lastEventId.orElse(0l))
                .forEach(dealService::saveDeal);
    }

}
