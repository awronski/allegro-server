package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Deal;

import java.util.List;
import java.util.Optional;

public interface IDealService {

    Optional<Long> findLastProcessedDealEventId(long sellerId);
    Optional<Long> findLastRowId(long sellerId);
    List<Deal> getLastDeals(long sellerId, int limit);
    List<Deal> getDealsAfterEventId(long sellerId, long eventId);
    List<Deal> getDealsByBuyerId(long sellerId, long buyerId);

    void saveDeal(Deal deal);
    void updateLastProcessedDealEventId(Deal deal);
    long createLastProcessedDealEventId(long sellerId);

}
