package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IAuctionService;
import com.apwglobal.nice.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AuctionController implements JsonpControllerAdvice, ClientIdAwareController {

    @Autowired
    private IAuctionService auctionService;

    @RequestMapping("/auctions")
    @ResponseBody
    public List<Auction> auctions(
            @RequestParam(value = "open", required = false) Optional<Boolean> open,
            @RequestParam(value = "withSale", required = false) Optional<Boolean> withSale,
            @RequestParam(value = "limit", required = false) Optional<Integer> limit) {
        return auctionService.getAuctions(getClientId(), open, withSale, limit);
    }

    @RequestMapping("/auctions/{itemId}")
    @ResponseBody
    public Optional<Auction> auctionById(@PathVariable("itemId") long itemId) {
        return auctionService.getAuctionById(getClientId(), itemId);
    }

    @RequestMapping("/auctions/{itemId}/fields")
    @ResponseBody
    public List<AuctionField> auctionFieldsById(@PathVariable("itemId") long itemId) {
        return auctionService.getAuctionFieldsById(getClientId(), itemId);
    }

    @RequestMapping(value = "/auctions/{itemId}/changeQty", method = RequestMethod.PUT)
    @ResponseBody
    public ChangedQty changeQty(@PathVariable("itemId") long itemId, @RequestParam("newQty") int newQty) {
        return auctionService.changeQty(getClientId(), itemId, newQty);
    }

    @RequestMapping(value = "/auctions/{itemId}/changePrice", method = RequestMethod.PUT)
    @ResponseBody
    public ChangedPrice changePrice(@PathVariable("itemId") long itemId, @RequestParam("newPrice") double newPrice) {
        return auctionService.changePrice(getClientId(), itemId, newPrice);
    }

    @RequestMapping(value = "/auctions/finish", method = RequestMethod.PUT)
    @ResponseBody
    public List<FinishAuctionFailure> finish(@RequestParam("itemsIds") List<Long> itemsIds) {
        return auctionService.finishAuctions(getClientId(), itemsIds);
    }

    @RequestMapping(value = "/auctions/create", method = RequestMethod.POST)
    @ResponseBody
    public CreatedAuction create(@RequestBody List<AuctionField> fields) {
        return auctionService.createNewAuction(getClientId(), fields);
    }

    @RequestMapping(value = "/auctions/{itemId}/change", method = RequestMethod.PUT)
    @ResponseBody
    public ChangedAuctionInfo change(@PathVariable("itemId") long itemId, @RequestBody List<AuctionField> fields) {
        return auctionService.changeAuctionFields(getClientId(), itemId, fields);
    }

}
