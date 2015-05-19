package com.apwglobal.allegro.server.service;

import com.apwglobal.nice.service.IAllegroNiceApi;

import java.util.List;

public interface IAllegroClientFactory {

    IAllegroNiceApi get(long sellerId);
    List<IAllegroNiceApi> getAll();

}
