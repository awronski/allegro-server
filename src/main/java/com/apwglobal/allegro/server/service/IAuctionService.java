package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.*;

import java.util.List;
import java.util.Optional;

public interface IAuctionService {

    List<Auction> getAuctions(Optional<Integer> limit);
    Optional<Auction> getAuctionById(long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);

    ChangedQty changeQty(long itemId, int newQty);
    List<FinishAuctionFailure> finishAuctions(List<Long> itemsIds);
    CreatedAuction createNewAuction(List<NewAuctionField> fields);

    AuctionStatus getAuctionStatusById(long itemId);
    List<AuctionStatus> getAuctionStatusesByStatus(AuctionStatusType status);

    void closeAuction(long itemId);

}
