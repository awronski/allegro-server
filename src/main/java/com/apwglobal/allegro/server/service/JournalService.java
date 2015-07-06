package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.JournalDao;
import com.apwglobal.nice.domain.Journal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JournalService implements IJournalService {

    private final static Logger logger = LoggerFactory.getLogger(JournalService.class);

    @Autowired
    private JournalDao journalDao;


    @Override
    public Optional<Long> findLastRowId(long sellerId) {
        return journalDao.findLastRowId(sellerId);
    }

    @Override
    public List<Journal> getLastJournals(long sellerId, int limit) {
        return journalDao.getLastJournals(sellerId, limit);
    }

    @Override
    public List<Journal> getJournalsAfterEventId(long sellerId, long lastEventId) {
        return journalDao.getJournalsAfterEventId(sellerId, lastEventId);
    }

    @Override
    public void saveJournal(Journal journal) {
        journalDao.saveJournal(journal);

        logger.debug("Saved: {}", journal);
    }


    @Override
    public long createLastProcessedJournalEventId(long sellerId) {
        journalDao.createLastProcessedJournalEventId(sellerId);

        logger.debug("Created Last processed journal event id for seller: {}", sellerId);
        return 0l;
    }

    @Override
    public Optional<Long> findLastProcessedJournalEventId(long sellerId) {
        return journalDao.findLastProcessedJournalEventId(sellerId);
    }

    @Override
    public void updateLastProcessedJournalEventId(Journal journal) {
        journalDao.updateLastProcessedJournalEventId(journal);

        logger.debug("Last processed journal: {}", journal);
    }
}
