package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.journal.Journal;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerTesty {

    private long lastJournalId = 0;

    @Autowired
    private IAllegroNiceApi allegro;

    @Scheduled(fixedDelay = 2500)
    public void getOffers() {
        allegro.login();
        List<Journal> journals = allegro.getJournal(lastJournalId).limit(100).toList().toBlocking().single();
        journals.forEach(System.out::println);
        if (journals.size() > 0) {
            lastJournalId = journals.get(journals.size() - 1).getRowId();
        }
    }
}
