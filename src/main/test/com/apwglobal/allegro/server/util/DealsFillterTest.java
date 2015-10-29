package com.apwglobal.allegro.server.util;

import com.apwglobal.nice.domain.Deal;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DealsFillterTest {

    private static Deal createDeal(long eventId, Long transactionId, long nowMinusMinutes) {
        Deal deal = mock(Deal.class);
        when(deal.getEventId()).thenReturn(eventId);
        when(deal.getTransactionId()).thenReturn(Optional.ofNullable(transactionId));
        when(deal.getEventTime()).thenReturn(Date.from(LocalDateTime.now().minusMinutes(nowMinusMinutes).atZone(ZoneId.systemDefault()).toInstant()));

        return deal;
    }

    @Test
    public void emptyDeals() throws Exception {
        List<Deal> deals = new ArrayList<>();

        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(0, deals.size());
    }

    @Test
    public void nothingToSkip() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[]{
                createDeal(0, 100L, 10),
                createDeal(1, null, 9),
                createDeal(2, 100L, 8)
                //--- here
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(3, deals.size());
        isOrderCorrect(deals);
    }

    @Test
    public void skipWithNoTransactionSplitInTheMiddle() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[]{
                createDeal(0, 100L, 10),
                createDeal(1, null, 9),
                createDeal(2, 100L, 8),
                //--- here
                createDeal(3, 200L, 4),
                createDeal(4, 200L, 4),
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(3, deals.size());
        isOrderCorrect(deals);
    }

    @Test
    public void skipWithTransactionSplitInTheMiddle() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[]{
                createDeal(0, 100L, 10),
                createDeal(1, null, 9),
                createDeal(2, 100L, 8),
                //--- here
                createDeal(3, 200L, 6),
                createDeal(4, 200L, 4),
                createDeal(5, 300L, 4),
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(3, deals.size());
        isOrderCorrect(deals);
    }

    @Test
    public void skipWithFewTransactionSplitInTheMiddle() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[] {
            createDeal(0, 100L, 10),
            createDeal(1, null, 10),
            //--- here
            createDeal(2, 200L, 9),
            createDeal(3, 200L, 9),
            createDeal(4, 300L, 8),
            createDeal(5, 300L, 5),
            createDeal(6, 200L, 4),
            createDeal(7, 300L, 3),
            createDeal(8, null, 2)
        }));

        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(2, deals.size());
        isOrderCorrect(deals);
    }

    @Test
    public void nothingLeftAfterSkip() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[] {
                //--- here
                createDeal(0, 100L, 10),
                createDeal(1, 200L, 10),
                createDeal(2, 200L, 1),
                createDeal(3, 100L, 1),
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(0, deals.size());
    }

    @Test
    public void skipWithGoodTransactionInTheMiddle() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[] {
                createDeal(0, 100L, 10),
                //--- here
                createDeal(1, 200L, 9),
                createDeal(3, 300L, 9),
                createDeal(4, 300L, 9),
                createDeal(5, 200L, 1),
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(1, deals.size());
    }

    @Test
    public void nothingLeftAfterGoodTransactionInTheMiddle() throws Exception {
        List<Deal> deals = new ArrayList<>(Arrays.asList( new Deal[] {
                //--- here
                createDeal(0, 100L, 10),
                createDeal(1, 200L, 10),
                createDeal(3, 100L, 10),
                createDeal(4, 200L, 1),
        }));
        deals = DealsFillter.skipLastDeals(deals, 5);
        assertEquals(0, deals.size());
    }

    private static void isOrderCorrect(List<Deal> deals) {
        IntStream.range(0, deals.size())
                .forEach(i -> assertEquals(i, deals.get(i).getEventId()));
    }
}