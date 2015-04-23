package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IFeedbackService;
import com.apwglobal.nice.domain.CreateFeedback;
import com.apwglobal.nice.domain.CreatedFeedback;
import com.apwglobal.nice.domain.WaitingFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedbackController implements IFeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @Override
    @RequestMapping("/feedbacks/waiting/onlyPaid")
    @ResponseBody
    public List<WaitingFeedback> waitingFeedbackOnlyForPaidOrders() {
        return feedbackService.getWaitingFeedbackOnlyForPaidOrders();
    }

    @RequestMapping(value = "/feedbacks/create", method = RequestMethod.POST)
    @ResponseBody
    public List<CreatedFeedback> create(@RequestBody List<CreateFeedback> feedbacks) {
        return feedbackService.createFeedbacks(feedbacks);
    }

}
