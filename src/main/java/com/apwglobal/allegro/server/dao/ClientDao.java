package com.apwglobal.allegro.server.dao;

import com.apwglobal.allegro.server.security.Client;

import java.util.List;

public interface ClientDao {

    List<Client> findAll();

}
