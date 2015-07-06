package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Journal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface JournalDao {

    Optional<Long> findLastRowId(long sellerId);
    List<Journal> getLastJournals(@Param("sellerId") long sellerId, @Param("limit") int limit);
    List<Journal> getJournalsAfterEventId(@Param("sellerId") long sellerId, @Param("eventId") long eventId);

    void saveJournal(Journal journal);

    void createLastProcessedJournalEventId(long sellerId);
    void updateLastProcessedJournalEventId(Journal journal);
    Optional<Long> findLastProcessedJournalEventId(long sellerId);

}
