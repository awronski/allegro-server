package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IAuctionService;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;
import java.util.Optional;

@Controller
public class AuctionController {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private IAuctionService auctionService;

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
        return auctionService.getAllAuctions();
    }

    @RequestMapping("/auctions/{itemId}")
    @ResponseBody
    public Optional<Auction> auctionById(@PathVariable("itemId") long itemId) {
        return auctionService.getAuctionById(itemId);
    }

    @RequestMapping(value = "/auctions/{itemId}/changeQty", method = RequestMethod.PUT)
    @ResponseBody
    public ChangedQty changeQty(@PathVariable("itemId") long itemId, @RequestParam("newQty") int newQty) {
        return auctionService.changeQty(itemId, newQty);
    }

    @RequestMapping(value = "/auctions/finish", method = RequestMethod.PUT)
    @ResponseBody
    public List<FinishAuctionFailure> finish(@RequestParam("itemsIds") List<Long> itemsIds) {
        return auctionService.finishAuctions(itemsIds);
    }

    @RequestMapping(value = "/auctions/create", method = RequestMethod.POST)
    @ResponseBody
    public CreatedAuction create(@RequestBody List<NewAuctionField> fields) {
        return auctionService.createNewAuction(fields);
    }

}
