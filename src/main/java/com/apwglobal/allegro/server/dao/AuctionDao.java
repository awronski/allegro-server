package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Auction;

import java.util.List;

public interface AuctionDao {

    List<Auction> getAllAuctions();
    Auction getAuctionById(long itemId);
    void createAuction(Auction auction);
    void updateAuction(Auction auction);

}
