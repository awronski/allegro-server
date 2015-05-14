package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    List<Payment> getPaymentsBetween(Optional<Date> from, Optional<Date> to);
    List<Payment> getLastPayments(int limit);

    void savePayment(Payment f);

    List<Payment> getUnprocessed();
    PaymentProcessed processed(long transactionId, double amount, String ref);

}
