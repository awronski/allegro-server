package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.DealDao;
import com.apwglobal.nice.domain.Deal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DealService implements IDealService {

    private final static Logger logger = LoggerFactory.getLogger(DealService.class);

    @Autowired
    private DealDao dealDao;

    @Override
    public Optional<Long> findLastProcessedDealEventId(long sellerId) {
        return dealDao.findLastProcessedDealEventId(sellerId);
    }

    @Override
    public Optional<Long> findLastRowId(long sellerId) {
        return dealDao.findLastRowId(sellerId);
    }

    @Override
    public List<Deal> getLastDeals(long sellerId, int limit) {
        return dealDao.getLastDeals(sellerId, limit);
    }

    @Override
    public List<Deal> getDealsAfterEventId(long sellerId, long eventId) {
        return dealDao.getDealsAfterEventId(sellerId, eventId);
    }

    @Override
    public List<Deal> getDealsByBuyerId(long sellerId, long buyerId) {
        return dealDao.getDealsByBuyerId(sellerId, buyerId);
    }

    @Override
    public void saveDeal(Deal deal) {
        dealDao.saveDeal(deal);

        logger.debug("Saved: {}", deal);
    }

    @Override
    public void updateLastProcessedDealEventId(Deal deal) {
        dealDao.updateLastProcessedDealEventId(deal);

        logger.debug("Last processed deal: {}", deal);
    }

    @Override
    public long createLastProcessedDealEventId(long sellerId) {
        dealDao.createLastProcessedDealEventId(sellerId);

        logger.debug("Created Last processed deal event id for seller: {}", sellerId);
        return 0l;
    }
}
