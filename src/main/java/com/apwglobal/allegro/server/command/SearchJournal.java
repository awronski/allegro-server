package com.apwglobal.allegro.server.command;

import com.apwglobal.nice.domain.JournalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

public class SearchJournal {

    private Optional<Integer> limit = Optional.empty();
    private Optional<Long> rowId = Optional.empty();
    private Optional<Long> itemId = Optional.empty();
    private Optional<JournalType> changeType = Optional.empty();

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Optional<Date> from = Optional.empty();

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Optional<Date> to = Optional.empty();

    public Optional<Integer> getLimit() {
        return limit;
    }
    public void setLimit(Optional<Integer> limit) {
        this.limit = limit;
    }
    public Optional<Long> getRowId() {
        return rowId;
    }
    public void setRowId(Optional<Long> rowId) {
        this.rowId = rowId;
    }
    public Optional<Long> getItemId() {
        return itemId;
    }
    public void setItemId(Optional<Long> itemId) {
        this.itemId = itemId;
    }
    public Optional<JournalType> getChangeType() {
        return changeType;
    }
    public void setChangeType(Optional<JournalType> changeType) {
        this.changeType = changeType;
    }
    public Optional<Date> getFrom() {
        return from;
    }
    public void setFrom(Optional<Date> from) {
        this.from = from;
    }
    public Optional<Date> getTo() {
        return to;
    }
    public void setTo(Optional<Date> to) {
        this.to = to;
    }

}
