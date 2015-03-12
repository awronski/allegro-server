package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Journal;

import java.util.Optional;

public interface JournalDao {

    Optional<Long> findLastRowId();
    void createJournal(Journal journal);

}
