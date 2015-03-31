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
public class AuctionController implements JsonpControllerAdvice {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private IAuctionService auctionService;

    @RequestMapping("/auctions")
    @ResponseBody
    public List<Auction> auctions(@RequestParam(value = "limit", required = false) Optional<Integer> limit) {
        return auctionService.getAuctions(limit);
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

    @RequestMapping("/auctions/check-status")
    @ResponseBody
    public AuctionStatus checkStatus(@RequestParam("itemId") long itemId) {
        return auctionService.getAuctionStatusById(itemId);
    }

    @RequestMapping("/auctions/by-status")
    @ResponseBody
    public List<AuctionStatus> byStatus(@RequestParam("status") AuctionStatusType status) {
        return auctionService.getAuctionStatusesByStatus(status);
    }

}
