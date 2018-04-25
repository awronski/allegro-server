package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IIncomingPaymentService;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncomingPaymentScheduler {

    private final static Logger logger = LoggerFactory.getLogger(IncomingPaymentScheduler.class);

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IIncomingPaymentService incomingPaymentService;

    @Scheduled(fixedDelay=20 * 60000)
    @Transactional
    public void syncIncomingPayments() {
        logger.debug("Starting syncIncomingPayments");
        allegro
                .getAll()
                .forEach(this::syncIncomingPaymentsForGivenClient);
    }

    private void syncIncomingPaymentsForGivenClient(IAllegroNiceApi client) {
        client
                .getIncomingPayments()
                .forEach(incomingPaymentService::createIfNotExist);
    }

}
