package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Journal;

import java.util.List;
import java.util.Optional;

public interface IJournalService {

    Optional<Long> findLastRowId();
    List<Journal> getLastJournals(int limit);

    void saveJournal(Journal journal);

}
