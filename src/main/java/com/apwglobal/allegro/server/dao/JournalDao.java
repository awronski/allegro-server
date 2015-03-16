package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Journal;

import java.util.List;
import java.util.Optional;

public interface JournalDao {

    Optional<Long> findLastRowId();
    List<Journal> getLastJournals(int limit);

    void saveJournal(Journal journal);

}
