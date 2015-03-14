package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.PostBuyForm;

import java.util.List;
import java.util.Optional;

public interface PostBuyFormDao {

    Optional<Long> findLastTransactionId();
    List<PostBuyForm> getLastPostBuyForms(int limit);

    void createAddress(Address address);
    void createPostBuyForm(PostBuyForm form);

}
