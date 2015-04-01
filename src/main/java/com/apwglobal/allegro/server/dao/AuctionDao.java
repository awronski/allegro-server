package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Auction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionDao {

    List<Auction> getAuctions(@Param("open") Optional<Boolean> open, @Param("limit") Optional<Integer> limit);
    Auction getAuctionById(long itemId);

    void saveAuction(Auction auction);
    void updateAuction(Auction auction);
    void closeAuction(long itemId);

}
