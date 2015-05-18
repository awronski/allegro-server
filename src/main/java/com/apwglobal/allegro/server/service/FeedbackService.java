package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.PaymentDao;
import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.FeedbackFor;
import com.apwglobal.nice.domain.WaitingFeedback;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FeedbackService implements IFeedbackService {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public List<WaitingFeedback> getWaitingFeedbackOnlyForPaidOrders() {
        Observable<WaitingFeedback> waitingFeedbacks = allegro.login().getWaitingFeedbacks();
        return waitingFeedbacks
                .filter(WaitingFeedback::isPossibilityToAdd)
                .filter(f -> f.getFeedbackFor() == FeedbackFor.BUYER)
                .filter(this::wasPaid)
                .toList()
                .toBlocking()
                .single();
    }

    private boolean wasPaid(WaitingFeedback waitingFeedback) {
        Date date = Date.from(LocalDateTime.now().minusDays(7).atZone(ZoneId.systemDefault()).toInstant());
        return paymentDao.wasItemPaid(date, waitingFeedback.getItemId(), waitingFeedback.getUserId());
    }

    @Override
    public List<CreatedFeedback> createFeedbacks(List<CreateFeedback> feedbacks) {
        return allegro.login().createFeedbacks(feedbacks);
    }

}