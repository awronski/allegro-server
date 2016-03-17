package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IAuctionService;
import com.apwglobal.allegro.server.service.IJournalService;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.Journal;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingLong;

@Service
public class AuctionScheduler {

    private final static Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IAuctionService auctionService;

    @Autowired
    private IJournalService journalService;

    @Scheduled(fixedDelay=11 * 60000)
    @Transactional
    public void syncAuctions() {
        logger.debug("Starting syncAuctions");
        allegro
                .getAll()
                .forEach(this::syncAuctions);
    }

    private void syncAuctions(IAllegroNiceApi client) {
        Long lastEventId = getLastEventId(client);
        List<Journal> journals = journalService.getJournalsAfterEventId(client.getClientId(), lastEventId);
        if (journals.isEmpty()) {
            return;
        }
        logger.debug("Journals events to process {} for seller {}", journals.size(), client.getClientId());

        client.login();
        journals
                .stream()
                .forEach(j -> processJournal(client, j));

        updateLastProcessedJournalEventId(journals);
    }

    private long getLastEventId(IAllegroNiceApi client) {
        Optional<Long> eventId = journalService.findLastProcessedJournalEventId(client.getClientId());
        long lastEventId;
        if (eventId.isPresent()) {
            lastEventId = eventId.get();
        } else {
            lastEventId = journalService.createLastProcessedJournalEventId(client.getClientId());
        }
        return lastEventId;
    }

    private void processJournal(IAllegroNiceApi client, Journal journal) {
        switch (journal.getChangeType()) {
            case CHANGE:
                updateAuction(client, journal);
                break;
            case START:
                saveAuction(client, journal);
                break;
            case END:
                endAuction(client, journal);
                break;
        }
    }

    private void endAuction(IAllegroNiceApi client, Journal journal) {
        auctionService.closeAuction(client.getClientId(), journal.getItemId());
    }

    private void saveAuction(IAllegroNiceApi client, Journal journal) {
        client.getAuctionById(journal.getItemId())
                .filter(a -> !exists(a))
                .ifPresent(a -> {
                    auctionService.saveAuction(a);
                    auctionService.updateAuctionExtraOptions(a.getSellerId(), a.getId());
                });
    }

    private void updateAuction(IAllegroNiceApi client, Journal journal) {
        client.getAuctionById(journal.getItemId())
                .filter(this::exists)
                .ifPresent(auctionService::updateAuction);
    }

    private void updateLastProcessedJournalEventId(List<Journal> journals) {
        journalService.updateLastProcessedJournalEventId(
                journals
                        .stream()
                        .max(comparingLong(Journal::getRowId))
                        .get()
        );
    }

    private boolean exists(Auction a) {
        return auctionService.getAuctionById(a.getSellerId(), a.getId()).isPresent();
    }

}
