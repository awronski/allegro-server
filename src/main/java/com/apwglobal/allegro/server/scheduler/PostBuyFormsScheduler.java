package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.dao.DealDao;
import com.apwglobal.allegro.server.dao.PostBuyFormDao;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.PostBuyForm;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.List;
import java.util.Optional;

@Service
public class PostBuyFormsScheduler {

    @Autowired
    private IAllegroNiceApi allegro;

    @Autowired
    private PostBuyFormDao postBuyFormDao;

    @Autowired
    private DealDao dealDao;

    @Scheduled(fixedDelay = 2 * 60000)
    @Transactional
    public void syncPostBuyForms() {

        Optional<Long> transactionId = postBuyFormDao.findLastTransactionId();
        List<Deal> deals = transactionId
                .map(dealDao::getDealsAfter)
                .orElse(dealDao.getDealsAfter(0));

        allegro
                .login()
                .getPostBuyForms(Observable.from(deals))
                .forEach(this::createPostBuyForm);
    }

    private void createPostBuyForm(PostBuyForm f) {
        createItems(f);
        createAddresses(f);
        postBuyFormDao.createPostBuyForm(f);
    }

    private void createAddresses(PostBuyForm f) {
        postBuyFormDao.createAddress(f.getOrderer());
        if (f.getOrderer() != f.getReceiver()) {
            postBuyFormDao.createAddress(f.getReceiver());
        }
    }

    private void createItems(PostBuyForm f) {
        f.getItems()
                .stream()
                .forEach(postBuyFormDao::createItem);
    }

}
