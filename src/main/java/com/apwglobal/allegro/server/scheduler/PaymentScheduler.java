package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.allegro.server.service.IPaymentService;
import com.apwglobal.allegro.server.util.DealsFillter;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingLong;

@Service
public class PaymentScheduler {

    private final static Logger logger = LoggerFactory.getLogger(PaymentScheduler.class);

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay = 15 * 60000)
    @Transactional
    public void syncPayments() {
        logger.debug("Starting syncPayments");
        allegro
                .getAll()
                .forEach(this::syncPaymentsForGivenClient);
    }

    private void syncPaymentsForGivenClient(IAllegroNiceApi client) {
        long lastEventId = getLastEventId(client);

        List<Deal> deals = dealService.getDealsAfterEventId(client.getClientId(), lastEventId);
        deals = DealsFillter.skipLastDeals(deals, 10);   //do not process deals from last 10 minutes because they can be incomplete

        if (deals.isEmpty()) {
            return;
        }

        client
                .getPayments(Observable.from(deals))
                .forEach(paymentService::savePayment);

        dealService.updateLastProcessedDealEventId(
                deals
                        .stream()
                        .max(comparingLong(Deal::getEventId))
                        .get()
        );
    }

    private long getLastEventId(IAllegroNiceApi client) {
        Optional<Long> eventId = dealService.findLastProcessedDealEventId(client.getClientId());
        return eventId.orElseGet(() -> dealService.createLastProcessedDealEventId(client.getClientId()));
    }

}
