package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentDao {

    Payment getPaymentById(@Param("sellerId") long sellerId, @Param("transactionId") long transactionId);
    List<Payment> getLastPayments(@Param("sellerId") long sellerId, @Param("limit") int limit);
    List<Payment> getPaymentsBetween(@Param("sellerId") long sellerId, @Param("from") Optional<Date> from, @Param("to") Optional<Date> to);

    void saveAddress(Address address);
    void savePayment(Payment form);
    void saveItem(Item item);

    void updatePaymentAsProcessed(@Param("transactionId") long transactionId, @Param("sellerId") long sellerId);

    PaymentProcessed findPaymentProcessed(@Param("transactionId") long transactionId, @Param("sellerId") long sellerId);
    void savePaymentProcessed(PaymentProcessed payment);
    List<Payment> getUnprocessed(long sellerId);

    boolean wasItemPaid(@Param("from") Date from, @Param("itemId") long itemId, @Param("buyerId") int buyerId);

}
