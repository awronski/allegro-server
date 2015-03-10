package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.db.AuctionDao;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuctionScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private AuctionDao auctionDao;

    @Scheduled(fixedDelay=60000)
    @Transactional
    public void syncAuctions() {
        allegro.login();
        allegro
                .getAuctions()
                .toList()
                .toBlocking()
                .single()
                .stream()
                .filter(a -> auctionDao.getAuctionById(a.getItemId()) == null)
                .forEach(auctionDao::createAuction);
    }
}
