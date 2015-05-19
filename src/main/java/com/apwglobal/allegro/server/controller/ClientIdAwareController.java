package com.apwglobal.allegro.server.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public interface ClientIdAwareController {

    default long getClientId() {
        return Long.valueOf(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        );
    }

}
