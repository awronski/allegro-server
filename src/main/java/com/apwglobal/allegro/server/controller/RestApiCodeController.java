package com.apwglobal.allegro.server.controller;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.nice.exception.RestApiException;
import com.apwglobal.nice.rest.RestApiSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RestApiCodeController implements IRestApiRestController, ClientIdAwareController {


    private final IAllegroClientFactory allegro;

    @Autowired
    public RestApiCodeController(IAllegroClientFactory allegro) {
        this.allegro = allegro;
    }

    @Override
    public String status() {
        RestApiSession restApiSession = allegro.get(getClientId()).getRestApiSession();
        return restApiSession != null ? restApiSession.toString() : "NOT-LOGGED";
    }

    @Override
    public String login(String code) {
        try {
            RestApiSession restApiSession = allegro.get(getClientId()).restLogin(code).getRestApiSession();
            return restApiSession.toString();
        } catch (RestApiException e) {
            return e.getMessage();
        }
    }
}
