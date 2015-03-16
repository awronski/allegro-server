package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.dao.PostBuyFormDao;
import com.apwglobal.nice.domain.PostBuyForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostBuyFormsService implements IPostBuyFormsService {

    private final static Logger logger = LoggerFactory.getLogger(PostBuyFormsService.class);

    @Autowired
    private PostBuyFormDao postBuyFormDao;

    @Override
    public Optional<Long> findLastTransactionId() {
        return postBuyFormDao.findLastTransactionId();
    }

    @Override
    public void savePostBuyForm(PostBuyForm f) {
        saveItems(f);
        saveAddresses(f);
        postBuyFormDao.savePostBuyForm(f);

        logger.debug("Saved: {}", f);
    }

    private void saveAddresses(PostBuyForm f) {
        postBuyFormDao.saveAddress(f.getOrderer());
        if (f.getOrderer() != f.getReceiver()) {
            postBuyFormDao.saveAddress(f.getReceiver());
        }
    }

    private void saveItems(PostBuyForm f) {
        f.getItems()
                .stream()
                .forEach(postBuyFormDao::saveItem);
    }

}
