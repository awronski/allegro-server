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
    public Optional<Long> findLastRowId() {
        return dealDao.findLastRowId();
    }

    @Override
    public List<Deal> getLastDeals(int limit) {
        return dealDao.getLastDeals(limit);
    }

    @Override
    public List<Deal> getDealsAfterTransactionId(long transactionId) {
        return dealDao.getDealsAfterTransactionId(transactionId);
    }

    @Override
    public List<Deal> getDealsAfterEventId(long eventId) {
        return dealDao.getDealsAfterEventId(eventId);
    }

    @Override
    public void saveDeal(Deal deal) {
        dealDao.saveDeal(deal);

        logger.debug("Saved: {}", deal);
    }
}
