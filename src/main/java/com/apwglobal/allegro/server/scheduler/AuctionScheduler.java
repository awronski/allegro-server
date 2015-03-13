package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.functions.Action1;
import rx.functions.Func1;

@Service
public class AuctionScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private AuctionDao auctionDao;

    @Scheduled(fixedDelay=9 * 60000)
    @Transactional
    public void syncAuctions() {
        doAuctions(
                a -> !exists(a),
                auctionDao::createAuction);
    }

    @Scheduled(fixedDelay=11 * 60000)
    @Transactional
    public void updateAuctions() {
        doAuctions(
                this::exists,
                auctionDao::updateAuction);
    }

    private boolean exists(Auction a) {
        return auctionDao.getAuctionById(a.getItemId()) != null;
    }

    private void doAuctions(Func1<Auction, Boolean> predicate, Action1<Auction> consumer) {
        allegro
                .login()
                .getAuctions()
                .filter(predicate)
                .forEach(consumer);
    }

}
