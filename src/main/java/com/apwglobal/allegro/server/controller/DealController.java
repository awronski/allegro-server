package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.nice.domain.Deal;
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
public class DealController {

    @Autowired
    private IDealService dealService;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable("deals")
    @RequestMapping("/deals")
    @ResponseBody
    public List<Deal> deals(@RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {
        return dealService.getLastDeals(limit);
    }

}
