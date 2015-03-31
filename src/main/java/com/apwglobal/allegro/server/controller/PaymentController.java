package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IPaymentService;
import com.apwglobal.nice.command.SearchPayment;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.apwglobal.allegro.server.controller.util.TestOptional.eq;
import static com.apwglobal.allegro.server.controller.util.TestOptional.exist;
import static java.util.stream.Collectors.toList;

@Controller
public class PaymentController implements IPaymentController {

    public static final int SEARCH_LIMIT = 100;

    @Autowired
    private IPaymentService paymentService;

    @Override
    public List<Payment> payments(@RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        return paymentService.getLastPayments(limit);
    }

    @Override
    public List<Payment> search(@RequestBody SearchPayment s) {
        return paymentService.getPaymentsBetween(s.getFrom(), s.getTo())
                .stream()
                .filter(f -> eq(s.getBuyerId(), f.getBuyerId()))
                .filter(f -> eq(s.getEmail(), f.getEmail()))
                .filter(f -> eq(s.getTransactionId(), f.getTransactionId()))
                .filter(f -> eq(s.getWithInvoice(), f.getWithInvoice()))
                .filter(f -> exist(s.getWithMsg(), f.getMsg()))
                .limit(s.getLimit().orElse(SEARCH_LIMIT))
                .collect(toList());
    }

    @Override
    public PaymentProcessed process(@RequestParam("transactionId") long transactionId,
                                    @RequestParam("amount") double amount,
                                    @RequestParam("ref") String ref) {

        return paymentService.processed(transactionId, amount, ref);
    }

    @Override
    public List<Payment> unprocessed() {
        return paymentService.getUnprocessed();
    }

}
