package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.PaymentProcessed;
import com.apwglobal.nice.domain.PostBuyForm;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostBuyFormDao {

    Optional<Long> findLastTransactionId();
    PostBuyForm getPostBuyFormById(long transactionId);
    List<PostBuyForm> getLastPostBuyForms(int limit);
    List<PostBuyForm> getPostBuyFormsBetween(@Param("from") Optional<Date> from, @Param("to")Optional<Date> to);

    void saveAddress(Address address);
    void savePostBuyForm(PostBuyForm form);
    void saveItem(Item item);

    PaymentProcessed findPaymentProcessed(long transactionId);
    void savePaymentProcessed(PaymentProcessed payment);

}
