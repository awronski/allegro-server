package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.AuctionDao;
import com.apwglobal.allegro.server.exception.ResourceNotFoundException;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.apwglobal.nice.domain.AuctionStatusType.*;
import static java.util.stream.Collectors.*;

@Service
@Transactional
public class AuctionService implements IAuctionService {

    private final static Logger logger = LoggerFactory.getLogger(AuctionService.class);

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private IAllegroNiceApi allegro;


    @Override
    public List<Auction> getAllAuctions() {
        return auctionDao.getAllAuctions();
    }

    @Override
    public Optional<Auction> getAuctionById(long itemId) {
        return Optional.ofNullable(auctionDao.getAuctionById(itemId));
    }

    @Override
    public void saveAuction(Auction auction) {
        AuctionStatus status = new AuctionStatus.Builder()
                .itemId(auction.getId())
                .status(OPEN)
                .ref("?")
                .build();
        auctionDao.saveAuction(auction);
        auctionDao.saveAuctionStatus(status);

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

        //TODO update auction in db

        return newAuction;
    }




    @Override
    public AuctionStatus getAuctionStatusById(long itemId) {
        return auctionDao.getAuctionStatusById(itemId);
    }

    @Override
    public List<AuctionStatus> getAuctionStatusesByStatus(AuctionStatusType status) {
        return auctionDao.getAuctionStatusesByStatus(status);
    }

    @Override
    public void closeAuction(long itemId) {
        auctionDao.closeAuctionStatus(itemId);
        logger.debug("Finished: {}", itemId);
    }

}
