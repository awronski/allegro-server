package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IDealService;
import com.apwglobal.allegro.server.service.IPaymentService;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IDealService dealService;

    @Scheduled(fixedDelay = 15 * 60000)
    @Transactional
    public void syncPayments() {

        Optional<Long> last = dealService.findLastProcessedDealEventId();
        List<Deal> deals = last
                .map(dealService::getDealsAfterEventId)
                .orElse(dealService.getDealsAfterEventId(0));

        if (deals.isEmpty()) {
            return;
        }

        allegro
                .login()
                .getPayments(Observable.from(deals))
                .forEach(paymentService::savePayment);

        dealService.updateLastProcessedDealEventId(
                deals
                .stream()
                .max(Comparator.comparingLong(Deal::getEventId))
                .get()
        );
    }

}
