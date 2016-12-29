package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.PaymentDao;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;
import com.apwglobal.nice.exception.UnknownAllegroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService implements IPaymentService {

    private final static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public List<Payment> getPaymentsBetween(long sellerId, Optional<Date> from, Optional<Date> to) {
        return paymentDao.getPaymentsBetween(sellerId, from, to);
    }

    @Override
    public List<Payment> getLastPayments(long sellerId, int limit) {
        return paymentDao.getLastPayments(sellerId, limit);
    }



    @Override
    @Transactional
    public void savePayment(Payment f) {
        Payment paymentById = paymentDao.getPaymentById(f.getSellerId(), f.getTransactionId());
        if (paymentById != null) {
            logger.error("Payment already exists. Cannot save again {}", paymentById);
        } else {
            savePaymentData(f);
        }
    }

    private void savePaymentData(Payment f) {
        logger.debug("Saving: {}", f);
        saveAddresses(f);
        paymentDao.savePayment(f);
        saveItems(f);

        logger.debug("Saved: {}", f);
    }

    private void saveAddresses(Payment p) {
        paymentDao.saveAddress(p.getOrderer());
        if (p.getOrderer() != p.getReceiver()) {
            paymentDao.saveAddress(p.getReceiver());
        }
    }

    private void saveItems(Payment p) {
        p.getItems().forEach(paymentDao::saveItem);
    }



    @Override
    public PaymentProcessed processed(long sellerId, long transactionId, double amount, String ref) {
        Payment payment = paymentDao.getPaymentById(sellerId, transactionId);
        check(payment, transactionId, amount);

        PaymentProcessed processed = paymentDao.findPaymentProcessed(transactionId, sellerId);
        if (processed == null) {
            processed = new PaymentProcessed.Builder()
                    .transactionId(transactionId)
                    .sellerId(sellerId)
                    .date(new Date())
                    .ref(ref)
                    .build();
            paymentDao.savePaymentProcessed(processed);
            paymentDao.updatePaymentAsProcessed(payment.getTransactionId(), sellerId);
            logger.debug("Saved: {}", processed);
        }
        return processed;
    }

    private void check(Payment p, long transactionId, double amount) {
        if (p == null || p.getTransactionId() != transactionId) {
            throw new UnknownAllegroException(String.format("Payment: %s, transactionId: %s, amount: %s", p, transactionId, amount));

        } else if (p.getAmount() != amount) {
            logger.warn("Amount is incorrect for transaction {}, is {}, got {}", transactionId, p.getAmount(), amount);
        }
    }

    @Override
    public List<Payment> getUnprocessed(long sellerId) {
        return paymentDao.getUnprocessed(sellerId);
    }
}
