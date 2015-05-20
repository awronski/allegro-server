package com.apwglobal.allegro.server.service;

import com.apwglobal.allegro.server.conf.AllegroProperties;
import com.apwglobal.allegro.server.dao.ClientDao;
import com.apwglobal.allegro.server.security.Client;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.service.AllegroNiceApi;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Service
public class AllegroClientFactory implements IAllegroClientFactory {

    private final static Logger logger = LoggerFactory.getLogger(AllegroClientFactory.class);

    @Autowired
    private AllegroProperties prop;

    @Autowired
    private ClientDao clientDao;

    private Map<Long, IAllegroNiceApi> allegroClients;

    @PostConstruct
    private void setup() {
        List<Client> clients = clientDao.findAll();
        if (clients.isEmpty()) {
            throw new IllegalStateException("Create at least one client in the database 'allegroClients' table");
        }

        this.allegroClients = Collections.synchronizedMap(
                clients
                    .stream()
                    .collect(toMap(Client::getClientId, this::createClient))
        );

        logger.debug("{} allegro clients were created", clients.size());
    }

    private AllegroNiceApi createClient(Client client) {
        com.apwglobal.nice.service.Configuration conf = new com.apwglobal.nice.service.Configuration(prop.getCountry());
        Credentials cred = new Credentials(client.getClientId(), client.getUsername(), client.getPassword(), client.getKey());
        return new AllegroNiceApi.Builder()
                .conf(conf)
                .cred(cred)
                .test(prop.isSandbox())
                .build();
    }


    @Override
    public IAllegroNiceApi get(long sellerId) {
        return allegroClients.get(sellerId);
    }

    @Override
    public List<IAllegroNiceApi> getAll() {
        return new ArrayList<>(allegroClients.values());
    }

}
