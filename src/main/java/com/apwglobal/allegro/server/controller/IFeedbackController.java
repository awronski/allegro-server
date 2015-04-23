package com.apwglobal.allegro.server.controller;

import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.WaitingFeedback;

import java.util.List;

public interface IFeedbackController {

    List<WaitingFeedback> waitingFeedbackOnlyForPaidOrders();
    List<CreatedFeedback> create(List<CreateFeedback> feedbacks);

}
