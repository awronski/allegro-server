package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.JournalDao;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JournalScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private JournalDao journalDao;

    @Scheduled(fixedDelay=5 * 60000)
    @Transactional
    public void syncJournal() {
        Optional<Long> lastRawId = journalDao.findLastRowId();

        allegro
                .login()
                .getJournal(lastRawId.orElse(0l))
                .forEach(journalDao::createJournal);
    }

}
