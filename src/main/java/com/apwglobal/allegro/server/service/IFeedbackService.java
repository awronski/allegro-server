package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.WaitingFeedback;

import java.util.List;

public interface IFeedbackService {

    List<WaitingFeedback> getWaitingFeedbackOnlyForPaidOrders();
    List<CreatedFeedback> createFeedbacks(List<CreateFeedback> feedbacks);

}
