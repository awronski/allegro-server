package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.allegro.server.exception.ResourceNotFoundException;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionService implements IAuctionService {

    private final static Logger logger = LoggerFactory.getLogger(AuctionService.class);

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private IAllegroNiceApi allegro;


    @Override
    public List<Auction> getAuctions(Optional<Boolean> open, Optional<Integer> limit) {
        return auctionDao.getAuctions(open, limit);
    }

    @Override
    public Optional<Auction> getAuctionById(long itemId) {
        return Optional.ofNullable(auctionDao.getAuctionById(itemId));
    }

    @Override
    public void saveAuction(Auction auction) {
        auctionDao.saveAuction(auction);

        logger.debug("Saved: {}", auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionDao.updateAuction(auction);

        logger.debug("Updated: {}", auction);
    }

    @Override
    public ChangedQty changeQty(long itemId, int newQty) {
        if (!getAuctionById(itemId).isPresent()) {
            throw new ResourceNotFoundException();
        }

        ChangedQty changedQty = allegro.changeQty(itemId, newQty);
        logger.debug("Changed qty: {}", changedQty);

        //TODO update auction in db

        return changedQty;
    }

    @Override
    public List<FinishAuctionFailure> finishAuctions(List<Long> itemsIds) {
        logger.debug("Finishing auctions: {}", itemsIds);
        List<FinishAuctionFailure> failures = allegro.finishAuctions(itemsIds);
        logger.debug("Finished auctions failures: {}", failures);

        return failures;
    }

    @Override
    public CreatedAuction createNewAuction(List<NewAuctionField> fields) {
        CreatedAuction newAuction = allegro.createNewAuction(fields);
        logger.debug("Created: {}", newAuction);

        return newAuction;
    }

    @Override
    public void closeAuction(long itemId) {
        auctionDao.closeAuction(itemId);
        logger.debug("Finished: {}", itemId);
    }

}
