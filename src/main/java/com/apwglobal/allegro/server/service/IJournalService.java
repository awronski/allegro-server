package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Journal;

import java.util.List;
import java.util.Optional;

public interface IJournalService {

    Optional<Long> findLastRowId(long sellerId);
    List<Journal> getLastJournals(long sellerId, int limit);
    List<Journal> getJournalsAfterEventId(long sellerId, long lastEventId);

    void saveJournal(Journal journal);

    long createLastProcessedJournalEventId(long sellerId);
    void updateLastProcessedJournalEventId(Journal journal);
    Optional<Long> findLastProcessedJournalEventId(long sellerId);

}
