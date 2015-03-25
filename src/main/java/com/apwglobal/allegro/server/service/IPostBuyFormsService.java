package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.domain.PostBuyForm;

import java.util.Optional;

public interface IPostBuyFormsService {

    Optional<Long> findLastTransactionId();
    void savePostBuyForm(PostBuyForm f);

}
