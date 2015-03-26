package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.AuctionStatus;

import java.util.List;

public interface AuctionDao {

    List<Auction> getAllAuctions();
    Auction getAuctionById(long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);

    AuctionStatus getAuctionStatusById(long itemId);
    void saveAuctionStatus(AuctionStatus status);
    void closeAuctionStatus(long itemId);

}
