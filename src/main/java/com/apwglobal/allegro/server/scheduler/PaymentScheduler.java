package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.allegro.server.service.IPaymentService;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.service.IAllegroNiceApi;
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

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay = 15 * 60000)
    @Transactional
    public void syncPayments() {
        allegro
                .getAll()
                .forEach(this::syncPaymentsForGivenClient);
    }

    private void syncPaymentsForGivenClient(IAllegroNiceApi client) {
        long lastEventId = getLastEventId(client);

        List<Deal> deals = dealService.getDealsAfterEventId(client.getClientId(), lastEventId);
        if (deals.isEmpty()) {
            return;
        }

        client
                .login()
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
        long lastEventId;
        if (eventId.isPresent()) {
            lastEventId = eventId.get();
        } else {
            lastEventId = dealService.createLastProcessedDealEventId(client.getClientId());
        }
        return lastEventId;
    }

}
