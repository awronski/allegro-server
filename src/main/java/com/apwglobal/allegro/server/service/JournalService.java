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
    public Optional<Long> findLastRowId() {
        return journalDao.findLastRowId();
    }

    @Override
    public List<Journal> getLastJournals(int limit) {
        return journalDao.getLastJournals(limit);
    }

    @Override
    public void saveJournal(Journal journal) {
        journalDao.saveJournal(journal);

        logger.debug("Saved: {}", journal);
    }
}
