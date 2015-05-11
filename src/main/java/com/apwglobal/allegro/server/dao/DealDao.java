package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Deal;

import java.util.List;
import java.util.Optional;

public interface DealDao {

    Optional<Long> findLastRowId();
    List<Deal> getLastDeals(int limit);
    List<Deal> getDealsAfterTransactionId(long transactionId);
    List<Deal> getDealsAfterEventId(long eventId);

    void saveDeal(Deal deal);

}
