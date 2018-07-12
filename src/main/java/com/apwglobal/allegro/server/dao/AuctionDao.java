package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Auction;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AuctionDao {

    List<Auction> getAuctions(@Param("sellerId") long sellerId, @Param("open") Optional<Boolean> open, @Param("withSale") Optional<Boolean> withSale, @Param("limit") Optional<Integer> limit);
    Auction getAuctionById(@Param("sellerId") long sellerId, @Param("itemId") long itemId);

    void saveAuction(Auction auction);
    void updateAuction(Auction auction);
    void updateAuctionExtraOptions(@Param("itemId") long itemId, @Param("extraOptions") int extraOptions);
    void closeAuction(@Param("sellerId") long sellerId, @Param("itemId") long itemId);

    void updateAuctionLastSale(@Param("sellerId") long sellerId, @Param("itemId") long itemId, @Param("eventTime") Date eventTime);
}
