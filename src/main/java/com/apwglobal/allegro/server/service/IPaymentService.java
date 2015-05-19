package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    List<Payment> getPaymentsBetween(long sellerId, Optional<Date> from, Optional<Date> to);
    List<Payment> getLastPayments(long sellerId, int limit);

    void savePayment(Payment f);

    List<Payment> getUnprocessed(long sellerId);
    PaymentProcessed processed(long sellerId, long transactionId, double amount, String ref);

}
