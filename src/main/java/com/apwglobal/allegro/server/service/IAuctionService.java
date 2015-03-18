package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Auction;

import java.util.List;

public interface IAuctionService {

    List<Auction> getAllAuctions();
    Auction getAuctionById(long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);

}
