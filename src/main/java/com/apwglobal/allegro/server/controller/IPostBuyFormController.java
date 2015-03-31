package com.apwglobal.allegro.server.controller;

import com.apwglobal.nice.command.SearchPostBuyForm;
import com.apwglobal.nice.domain.PaymentProcessed;
import com.apwglobal.nice.domain.PostBuyForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IPostBuyFormController extends JsonpControllerAdvice {

    @RequestMapping("/forms")
    @ResponseBody
    List<PostBuyForm> postBuyForms(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit);

    @RequestMapping(value = "/forms/search", method = RequestMethod.POST)
    @ResponseBody
    List<PostBuyForm> search(@RequestBody SearchPostBuyForm s);

    @RequestMapping(value = "/forms/process", method = RequestMethod.PUT)
    @ResponseBody
    PaymentProcessed process(@RequestParam("transactionId") long transactionId,
                             @RequestParam("amount") double amount,
                             @RequestParam("ref") String ref);

    @RequestMapping(value = "/forms/unprocessed")
    @ResponseBody
    List<PostBuyForm> unprocessed();

}
