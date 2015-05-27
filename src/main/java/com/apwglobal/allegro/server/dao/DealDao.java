package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Deal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface DealDao {

    Optional<Long> findLastProcessedDealEventId(long sellerId);
    Optional<Long> findLastRowId(long sellerId);
    List<Deal> getLastDeals(@Param("sellerId") long sellerId, @Param("limit") int limit);
    List<Deal> getDealsAfterEventId(@Param("sellerId") long sellerId, @Param("eventId") long eventId);
    List<Deal> getDealsByBuyerId(@Param("sellerId") long sellerId, @Param("buyerId") long buyerId);


    void saveDeal(Deal deal);

    void updateLastProcessedDealEventId(Deal deal);
    void createLastProcessedDealEventId(long sellerId);

}
