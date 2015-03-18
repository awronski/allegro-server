package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IJournalService;
import com.apwglobal.nice.domain.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;

@Controller
public class JournalController {

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
    public List<Journal> journal(@RequestParam(value="limit",required=false,defaultValue="50") int limit) {
        return journalService.getLastJournals(limit);
    }

}
