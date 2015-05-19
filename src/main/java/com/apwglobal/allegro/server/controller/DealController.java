package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.nice.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DealController implements JsonpControllerAdvice, ClientIdAwareController {

    @Autowired
    private IDealService dealService;

    @RequestMapping("/deals")
    @ResponseBody
    public List<Deal> deals(@RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {
        return dealService.getLastDeals(getClientId(), limit);
    }

    @RequestMapping("/deals/after/eventId/{eventId}")
    @ResponseBody
    public List<Deal> dealsAfterEventId(@PathVariable(value = "eventId") long eventId) {
        return dealService.getDealsAfterEventId(getClientId(), eventId);
    }

}
