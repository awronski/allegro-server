package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.PaymentProcessed;
import com.apwglobal.nice.domain.PostBuyForm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPostBuyFormsService {

    Optional<Long> findLastTransactionId();

    List<PostBuyForm> getPostBuyFormsBetween(Optional<Date> from, Optional<Date> to);
    List<PostBuyForm> getLastPostBuyForms(int limit);

    void savePostBuyForm(PostBuyForm f);

    PaymentProcessed processed(long transactionId, double amount, String ref);



}
