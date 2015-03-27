package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.PostBuyFormDao;
import com.apwglobal.nice.domain.PaymentProcessed;
import com.apwglobal.nice.domain.PostBuyForm;
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
public class PostBuyFormsService implements IPostBuyFormsService {

    private final static Logger logger = LoggerFactory.getLogger(PostBuyFormsService.class);

    @Autowired
    private PostBuyFormDao postBuyFormDao;

    @Override
    public Optional<Long> findLastTransactionId() {
        return postBuyFormDao.findLastTransactionId();
    }

    @Override
    public List<PostBuyForm> getPostBuyFormsBetween(Optional<Date> from, Optional<Date> to) {
        return postBuyFormDao.getPostBuyFormsBetween(from, to);
    }

    @Override
    public List<PostBuyForm> getLastPostBuyForms(int limit) {
        return postBuyFormDao.getLastPostBuyForms(limit);
    }



    @Override
    public void savePostBuyForm(PostBuyForm f) {
        saveItems(f);
        saveAddresses(f);
        postBuyFormDao.savePostBuyForm(f);

        logger.debug("Saved: {}", f);
    }

    private void saveAddresses(PostBuyForm f) {
        postBuyFormDao.saveAddress(f.getOrderer());
        if (f.getOrderer() != f.getReceiver()) {
            postBuyFormDao.saveAddress(f.getReceiver());
        }
    }

    private void saveItems(PostBuyForm f) {
        f.getItems()
                .stream()
                .forEach(postBuyFormDao::saveItem);
    }



    @Override
    public PaymentProcessed processed(long transactionId, double amount, String ref) {
        PostBuyForm f = postBuyFormDao.getPostBuyFormById(transactionId);
        check(f, transactionId, amount);

        PaymentProcessed payment = postBuyFormDao.findPaymentProcessed(transactionId);
        if (payment == null) {
            payment = new PaymentProcessed.Builder()
                    .transactionId(transactionId)
                    .date(new Date())
                    .ref(ref)
                    .build();
            postBuyFormDao.savePaymentProcessed(payment);
            logger.debug("Saved: {}", payment);
        }
        return payment;
    }

    private void check(PostBuyForm f, long transactionId, double amount) {
        //TODO add better exception handling
        if (f == null || f.getTransactionId() != transactionId || f.getAmount() != amount) {
            throw new IllegalArgumentException(
                    String.format("PostBuyForm: %s, transactionId: %s, amount: %s", f, transactionId, amount));
        }
    }
}
