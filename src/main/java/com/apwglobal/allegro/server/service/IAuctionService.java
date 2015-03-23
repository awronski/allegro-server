package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.ChangedQty;

import java.util.List;
import java.util.Optional;

public interface IAuctionService {

    List<Auction> getAllAuctions();
    Optional<Auction> getAuctionById(long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);

    ChangedQty changeQty(long itemId, int newQty);

}
