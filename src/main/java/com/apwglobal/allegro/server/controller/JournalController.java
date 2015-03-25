package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IJournalService;
import com.apwglobal.nice.command.SearchJournal;
import com.apwglobal.nice.domain.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class JournalController {

    public static final int SEARCH_LIMIT = 100;

    @Autowired
    private IJournalService journalService;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable("journals")
    @RequestMapping("/journals")
    @ResponseBody
    public List<Journal> journal(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        return journalService.getLastJournals(limit);
    }

    @RequestMapping(value = "/journals/search", method = RequestMethod.POST)
    @ResponseBody
    public List<Journal> search(@RequestBody SearchJournal s) {
        return journalService.getLastJournals(s.getLimit().orElse(SEARCH_LIMIT))
                .stream()
                .filter(j -> testEquals(s.getItemId(), j.getItemId()))
                .filter(j -> testEquals(s.getRowId(), j.getRowId()))
                .filter(j -> testEquals(s.getChangeType(), j.getChangeType()))
                .filter(j -> testGe(s.getFrom(), j.getChangeDate()))
                .filter(j -> testLe(s.getTo(), j.getChangeDate()))
                .collect(Collectors.toList());
    }

    private boolean testEquals(Optional o, Object v) {
        return !o.isPresent() || o.get().equals(v);
    }

    @SuppressWarnings("unchecked")
    private boolean testGe(Optional<? extends Comparable> o, Comparable v) {
        return !o.isPresent() || v.compareTo(o.get()) >= 0;
    }

    @SuppressWarnings("unchecked")
    private boolean testLe(Optional<? extends Comparable> o, Comparable v) {
        return !o.isPresent() || v.compareTo(o.get()) <= 0;
    }

}
