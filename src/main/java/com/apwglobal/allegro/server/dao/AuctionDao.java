package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.AuctionStatus;
import com.apwglobal.nice.domain.AuctionStatusType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionDao {

    List<Auction> getAuctions(@Param("limit") Optional<Integer> limit);
    Auction getAuctionById(long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);

    AuctionStatus getAuctionStatusById(long itemId);
    List<AuctionStatus> getAuctionStatusesByStatus(AuctionStatusType status);
    void saveAuctionStatus(AuctionStatus status);
    void closeAuctionStatus(long itemId);

}
