package com.apwglobal.allegro.server.util;

import com.apwglobal.nice.domain.Deal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DealsFillter {

    private final static Logger logger = LoggerFactory.getLogger(DealsFillter.class);

    public static List<Deal> skipLastDeals(List<Deal> deals, int sinceMinutes) {
        if (deals.isEmpty()) {
            return deals;
        }

        Date since = Date.from(LocalDateTime.now().minusMinutes(sinceMinutes).atZone(ZoneId.systemDefault()).toInstant());
        List<Deal> removed = new ArrayList<>();
        Optional<Deal> lastRemoved = Optional.empty();

        for (int i = deals.size() - 1; i >= 0; i--) {
            Deal deal = deals.get(i);
            if (transactionIsAfterGivenTime(since, deal) || transactionWithThisIdWasRemovedBefore(deal, removed)) {
                lastRemoved = Optional.of(deal);
                removed = deals.subList(i, deals.size());
            }
        }

        logger.debug("Removed deals = {}", removed);

        return lastRemoved.isPresent()
                ? deals.subList(0, deals.indexOf(lastRemoved.get()))
                : deals;
    }

    private static boolean transactionIsAfterGivenTime(Date since, Deal deal) {
        return deal.getEventTime().compareTo(since) > 0;
    }

    private static boolean transactionWithThisIdWasRemovedBefore(Deal deal, List<Deal> removed) {
        return deal.getTransactionId().isPresent()
                && removed
                    .stream()
                    .filter(d -> d.getTransactionId().isPresent())
                    .filter(d -> d.getTransactionId().get().equals(deal.getTransactionId().get()))
                    .findAny()
                    .isPresent();
    }

}
