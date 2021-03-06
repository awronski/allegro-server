package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.IncomingPayment;

import java.util.List;

public interface IIncomingPaymentService {

    List<IncomingPayment> getLastIncomingPayments(long sellerId, int limit);
    List<IncomingPayment> getIncomingPaymentsSurcharges(long clientId, int limit);

    void createIfNotExist(IncomingPayment incomingPayment);

}
