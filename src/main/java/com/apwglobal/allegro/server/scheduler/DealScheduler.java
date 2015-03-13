package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.DealDao;
import com.apwglobal.allegro.server.dao.JournalDao;
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
    private DealDao dealDao;

    @Scheduled(fixedDelay=2 * 60000)
    @Transactional
    public void syncJournalAuctions() {
        Optional<Long> lastEventId = dealDao.findLastRowId();

        allegro
                .login()
                .getDeals(lastEventId.orElse(0l))
                .forEach(dealDao::createDeal);
    }

}
