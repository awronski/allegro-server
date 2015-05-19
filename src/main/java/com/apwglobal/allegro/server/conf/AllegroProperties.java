package com.apwglobal.allegro.server.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="allegro")
@Configuration
public class AllegroProperties {

    private int country=1;
    private boolean sandbox=false;

    public int getCountry() {
        return country;
    }
    public void setCountry(int country) {
        this.country = country;
    }
    public boolean isSandbox() {
        return sandbox;
    }
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

}
