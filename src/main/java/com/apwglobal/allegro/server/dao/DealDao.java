package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.Journal;

import java.util.List;
import java.util.Optional;

public interface DealDao {

    Optional<Long> findLastRowId();
    List<Deal> getLastDeals(int limit);
    List<Deal> getDealsAfter(long transactionId);
    void createDeal(Deal deal);

}
