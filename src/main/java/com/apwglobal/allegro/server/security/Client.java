package com.apwglobal.allegro.server.security;

public class Client {

    private long clientId;
    private String username;
    private String password;
    private String key;

    private String restClientId;
    private String restClientSecret;
    private String restClientApiKey;
    private String restRedirectUri;

    public long getClientId() {
        return clientId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getKey() {
        return key;
    }
    public String getRestClientId() {
        return restClientId;
    }
    public String getRestClientSecret() {
        return restClientSecret;
    }
    public String getRestClientApiKey() {
        return restClientApiKey;
    }
    public String getRestRedirectUri() {
        return restRedirectUri;
    }

}
