package com.apwglobal.allegro.server.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Jdk8ModuleConfiguration {

    @Autowired
    public ObjectMapper mapper;

    @PostConstruct
    public void setup() {
        mapper.registerModule(new Jdk8Module());
    }

}
