package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.allegro.server.exception.ResourceNotFoundException;
import com.apwglobal.nice.domain.*;
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
    private IAllegroClientFactory allegro;


    @Override
    public List<Auction> getAuctions(long sellerId, Optional<Boolean> open, Optional<Integer> limit) {
        return auctionDao.getAuctions(sellerId, open, limit);
    }

    @Override
    public Optional<Auction> getAuctionById(long sellerId, long itemId) {
        return Optional.ofNullable(auctionDao.getAuctionById(sellerId, itemId));
    }

    @Override
    public List<AuctionField> getAuctionFieldsById(long sellerId, long itemId) {
        return allegro.get(sellerId).getAuctionFields(itemId);
    }

    @Override
    public void saveAuction(Auction auction) {
        auctionDao.saveAuction(auction);

        logger.debug("Saved: {}", auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctionDao.updateAuction(auction);

        logger.trace("Updated: {}", auction);
    }

    @Override
    public ChangedQty changeQty(long sellerId, long itemId, int newQty) {
        if (!getAuctionById(sellerId, itemId).isPresent()) {
            throw new ResourceNotFoundException();
        }

        ChangedQty changedQty = allegro.get(sellerId).changeQty(itemId, newQty);
        logger.debug("Changed qty: {}", changedQty);

        //TODO update auction in db

        return changedQty;
    }

    @Override
    public ChangedPrice changePrice(long sellerId, long itemId, double newPrice) {
        if (!getAuctionById(sellerId, itemId).isPresent()) {
            throw new ResourceNotFoundException();
        }

        ChangedPrice changedPrice = allegro.get(sellerId).changePrice(itemId, newPrice);
        logger.debug("Changed price: {}, {}", newPrice, changedPrice.getInfo());

        //TODO update auction in db

        return changedPrice;
    }

    @Override
    public List<FinishAuctionFailure> finishAuctions(long sellerId, List<Long> itemsIds) {
        logger.debug("Finishing auctions: {}", itemsIds);
        List<FinishAuctionFailure> failures = allegro.get(sellerId).finishAuctions(itemsIds);
        logger.debug("Finished auctions failures: {}", failures);

        return failures;
    }

    @Override
    public CreatedAuction createNewAuction(long sellerId, List<AuctionField> fields) {
        CreatedAuction newAuction = allegro.get(sellerId).createNewAuction(fields);
        logger.debug("Created: {}", newAuction);

        return newAuction;
    }

    @Override
    public ChangedAuctionInfo changeAuctionFields(long sellerId, long itemId, List<AuctionField> fields) {
        ChangedAuctionInfo info = allegro.get(sellerId).changeAuctions(itemId, fields);
        logger.debug("Changed: {}", info);

        return info;
    }

    @Override
    public void closeAuction(long sellerId, long itemId) {
        auctionDao.closeAuction(sellerId, itemId);
        logger.debug("Finished: {}", itemId);
    }

}
