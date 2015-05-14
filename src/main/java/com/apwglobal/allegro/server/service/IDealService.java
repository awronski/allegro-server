package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Deal;

import java.util.List;
import java.util.Optional;

public interface IDealService {

    Optional<Long> findLastProcessedDealEventId();
    Optional<Long> findLastRowId();
    List<Deal> getLastDeals(int limit);
    List<Deal> getDealsAfterEventId(long eventId);

    void saveDeal(Deal deal);
    void updateLastProcessedDealEventId(Deal deal);

}
