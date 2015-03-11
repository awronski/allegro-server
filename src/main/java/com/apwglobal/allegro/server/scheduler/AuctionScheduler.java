package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Auction> auctionList = allegro
                .getAuctions()
                .toList()
                .toBlocking()
                .single();

        auctionList
                .stream()
                .filter(a -> auctionDao.getAuctionById(a.getItemId()) == null)
                .forEach(auctionDao::createAuction);
    }
}
