package com.apwglobal.allegro.server.controller;

import com.apwglobal.nice.domain.Journal;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;

@Controller
public class JournalController {

    @Autowired
    private IAllegroNiceApi allegro;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable("journal")
    @RequestMapping("/journal")
    @ResponseBody
    public List<Journal> journal() {
        allegro.login();
        return allegro
                .getJournal(0)
                .toList()
                .toBlocking()
                .single();
    }

}
