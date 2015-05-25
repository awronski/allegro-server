package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IIncomingPaymentService;
import com.apwglobal.nice.domain.IncomingPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IncomingPaymentController implements JsonpControllerAdvice, ClientIdAwareController {

    @Autowired
    private IIncomingPaymentService incomingPaymentService;

    @RequestMapping("/incoming-payments")
    @ResponseBody
    public List<IncomingPayment> incomingPayments(@RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {
        return incomingPaymentService.getLastIncomingPayments(getClientId(), limit);
    }

}
