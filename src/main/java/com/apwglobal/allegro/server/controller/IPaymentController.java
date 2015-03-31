package com.apwglobal.allegro.server.controller;

import com.apwglobal.nice.command.SearchPayment;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IPaymentController extends JsonpControllerAdvice {

    @RequestMapping("/payments")
    @ResponseBody
    List<Payment> payments(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit);

    @RequestMapping(value = "/payments/search", method = RequestMethod.POST)
    @ResponseBody
    List<Payment> search(@RequestBody SearchPayment s);

    @RequestMapping(value = "/payments/process", method = RequestMethod.PUT)
    @ResponseBody
    PaymentProcessed process(@RequestParam("transactionId") long transactionId,
                             @RequestParam("amount") double amount,
                             @RequestParam("ref") String ref);

    @RequestMapping(value = "/payments/unprocessed")
    @ResponseBody
    List<Payment> unprocessed();

}
