package com.apwglobal.allegro.server.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix="allegro")
@Configuration
public class AllegroProperties {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String key;
    private int country=1;
    private boolean sandbox=false;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getCountry() {
        return country;
    }
    public void setCountry(int country) {
        this.country = country;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public boolean isSandbox() {
        return sandbox;
    }
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

}
