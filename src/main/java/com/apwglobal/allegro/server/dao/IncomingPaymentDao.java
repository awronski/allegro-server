package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.IncomingPayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomingPaymentDao {

    List<IncomingPayment> getLastIncomingPayments(@Param("sellerId") long sellerId, @Param("limit") int limit);
    List<IncomingPayment> getIncomingPaymentsSurcharges(@Param("sellerId") long sellerId, @Param("limit") int limit);

    boolean checkIfIncomingPaymentNotExist(long transactionId);
    void saveIncomingPayment(IncomingPayment incomingPayment);

}
