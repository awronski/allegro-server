package com.apwglobal.allegro.server.dao;

import com.apwglobal.nice.domain.Address;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.Item;
import com.apwglobal.nice.domain.PostBuyForm;

import java.util.List;
import java.util.Optional;

public interface PostBuyFormDao {

    Optional<Long> findLastTransactionId();
    List<PostBuyForm> getLastPostBuyForms(int limit);

    void saveAddress(Address address);
    void savePostBuyForm(PostBuyForm form);
    void saveItem(Item item);

}
