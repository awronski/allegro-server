package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.PaymentDao;
import com.apwglobal.nice.domain.Payment;
import com.apwglobal.nice.domain.PaymentProcessed;
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
    public Optional<Long> findLastTransactionId() {
        return paymentDao.findLastTransactionId();
    }

    @Override
    public List<Payment> getPaymentsBetween(Optional<Date> from, Optional<Date> to) {
        return paymentDao.getPaymentsBetween(from, to);
    }

    @Override
    public List<Payment> getLastPayments(int limit) {
        return paymentDao.getLastPayments(limit);
    }



    @Override
    @Transactional
    public void savePayment(Payment f) {
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
        p.getItems()
                .stream()
                .forEach(paymentDao::saveItem);
    }



    @Override
    public PaymentProcessed processed(long transactionId, double amount, String ref) {
        Payment payment = paymentDao.getPaymentById(transactionId);
        check(payment, transactionId, amount);

        PaymentProcessed processed = paymentDao.findPaymentProcessed(transactionId);
        if (processed == null) {
            processed = new PaymentProcessed.Builder()
                    .transactionId(transactionId)
                    .date(new Date())
                    .ref(ref)
                    .build();
            paymentDao.savePaymentProcessed(processed);
            paymentDao.updatePaymentAsProcessed(payment.getTransactionId());
            logger.debug("Saved: {}", processed);
        }
        return processed;
    }

    private void check(Payment p, long transactionId, double amount) {
        //TODO add better exception handling
        if (p == null || p.getTransactionId() != transactionId || p.getAmount() != amount) {
            throw new IllegalArgumentException(
                    String.format("Payment: %s, transactionId: %s, amount: %s", p, transactionId, amount));
        }
    }

    @Override
    public List<Payment> getUnprocessed() {
        return paymentDao.getUnprocessed();
    }
}
