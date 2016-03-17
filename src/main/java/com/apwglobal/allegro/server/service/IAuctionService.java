package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.*;

import java.util.List;
import java.util.Optional;

public interface IAuctionService {

    List<Auction> getAuctions(long sellerId, Optional<Boolean> open, Optional<Boolean> withSale, Optional<Integer> limit);
    Optional<Auction> getAuctionById(long sellerId, long itemId);
    List<AuctionField> getAuctionFieldsById(long sellerId, long itemId);
    void saveAuction(Auction auction);
    void updateAuction(Auction auction);
    boolean updateAuctionExtraOptions(long sellerId, long itemId);

    ChangedQty changeQty(long sellerId, long itemId, int newQty);
    ChangedPrice changePrice(long sellerId, long itemId, double newPrice);
    List<FinishAuctionFailure> finishAuctions(long sellerId, List<Long> itemsIds);
    CreatedAuction createNewAuction(long sellerId, List<AuctionField> fields);
    ChangedAuctionInfo changeAuctionFields(long sellerId, long itemId, List<AuctionField> fields);

    void closeAuction(long sellerId, long itemId);

}
