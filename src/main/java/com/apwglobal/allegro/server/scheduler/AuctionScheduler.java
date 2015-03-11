package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class AuctionScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private AuctionDao auctionDao;

    @Scheduled(fixedDelay=2 * 60000)
    @Transactional
    public void syncAuctions() {
        doAuctions(
                a -> !exists(a),
                auctionDao::createAuction);
    }

    @Scheduled(fixedDelay=3 * 60000)
    @Transactional
    public void updateAuctions() {
        doAuctions(
                this::exists,
                auctionDao::updateAuction);
    }

    private boolean exists(Auction a) {
        return auctionDao.getAuctionById(a.getItemId()) != null;
    }

    private void doAuctions(Predicate<Auction> predicate, Consumer<Auction> consumer) {
        allegro.login();

        List<Auction> auctionList = allegro
                    .getAuctions()
                    .toList()
                    .toBlocking()
                    .single();

        auctionList
                .stream()
                .filter(predicate)
                .forEach(consumer);
    }

}
