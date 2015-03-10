package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.db.AuctionDao;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;

@Controller
public class AuctionController {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private AuctionDao auctionDao;

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }

    @Cacheable("auctions")
    @RequestMapping("/auctions")
    @ResponseBody
    public List<Auction> allAuctions() {
        return auctionDao.getAllAuctions();
    }

    @RequestMapping("/auctions/{itemId}")
    @ResponseBody
    public Auction auctionById(@PathVariable("itemId") long itemId) {
        return auctionDao.getAuctionById(itemId);
    }

}
