package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IJournalService;
import com.apwglobal.nice.command.SearchJournal;
import com.apwglobal.nice.domain.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.apwglobal.allegro.server.controller.util.TestOptional.*;
import static java.util.stream.Collectors.toList;

@Controller
public class JournalController implements JsonpControllerAdvice, ClientIdAwareController {

    public static final int SEARCH_LIMIT = 100;

    @Autowired
    private IJournalService journalService;

    @RequestMapping("/journals")
    @ResponseBody
    public List<Journal> journal(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        return journalService.getLastJournals(getClientId(), limit);
    }

    @RequestMapping(value = "/journals/search", method = RequestMethod.POST)
    @ResponseBody
    public List<Journal> search(@RequestBody SearchJournal s) {
        return journalService.getLastJournals(getClientId(), s.getLimit().orElse(SEARCH_LIMIT))
                .stream()
                .filter(j -> eq(s.getItemId(), j.getItemId()))
                .filter(j -> eq(s.getRowId(), j.getRowId()))
                .filter(j -> eq(s.getChangeType(), j.getChangeType()))
                .filter(j -> ge(s.getFrom(), j.getChangeDate()))
                .filter(j -> le(s.getTo(), j.getChangeDate()))
                .collect(toList());
    }

}
