package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.IncomingPaymentDao;
import com.apwglobal.nice.domain.IncomingPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IncomingPaymentService implements IIncomingPaymentService {

    private final static Logger logger = LoggerFactory.getLogger(IncomingPaymentService.class);

    @Autowired
    private IncomingPaymentDao incomingPaymentDao;

    @Override
    public List<IncomingPayment> getLastIncomingPayments(long sellerId, int limit) {
        return incomingPaymentDao.getLastIncomingPayments(sellerId, limit);
    }

    @Override
    public List<IncomingPayment> getIncomingPaymentsSurcharges(long sellerId, int limit) {
        return incomingPaymentDao.getIncomingPaymentsSurcharges(sellerId, limit);
    }

    @Override
    public void createIfNotExist(IncomingPayment ip) {
        if (incomingPaymentDao.checkIfIncomingPaymentNotExist(ip.getSellerId(), ip.getTransactionId())) {
            incomingPaymentDao.saveIncomingPayment(ip);
            logger.debug("Saved: {}", ip);
        }
    }
}
