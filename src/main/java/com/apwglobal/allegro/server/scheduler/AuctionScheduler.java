package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IAuctionService;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AuctionScheduler {

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IAuctionService auctionService;

    @Scheduled(fixedDelay=11 * 60000)
    @Transactional
    public void syncAuctions() {
        doAuctions(
                a -> !exists(a),
                auctionService::saveAuction);
    }

    @Scheduled(fixedDelay=9 * 60000)
    @Transactional
    public void updateAuctions() {
        doAuctions(
                this::exists,
                auctionService::updateAuction);
    }

    private boolean exists(Auction a) {
        return auctionService.getAuctionById(a.getSellerId(), a.getId()).isPresent();
    }

    private void doAuctions(Func1<Auction, Boolean> predicate, Action1<Auction> consumer) {
        allegro
                .getAll()
                .stream()
                .forEach(c -> c.login()
                        .getAuctions()
                        .filter(predicate)
                        .forEach(consumer));
    }



    @Scheduled(fixedDelay=13 * 60000)
    @Transactional
    public void closeAuctions() {
        allegro
                .getAll()
                .forEach(this::closeAuctionsForGivenClient);
    }

    private void closeAuctionsForGivenClient(IAllegroNiceApi client) {
        List<Long> inDb = auctionService.getAuctions(client.getClientId(), Optional.of(true), Optional.empty())
                .stream()
                .map(Auction::getId)
                .collect(toList());

        if (inDb.isEmpty()) {
            return;
        }

        //TODO what to do if auction is open but allegro did not refreshed list yet?
        List<Long> inAllegro = client
                .login()
                .getAuctions()
                .map(Auction::getId)
                .toList()
                .toBlocking()
                .single();

        inDb
                .stream()
                .filter(a -> !inAllegro.contains(a))
                .forEach(a -> auctionService.closeAuction(client.getClientId(), a));
    }

}
