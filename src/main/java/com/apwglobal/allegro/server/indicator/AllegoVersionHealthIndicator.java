package com.apwglobal.allegro.server.indicator;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import pl.allegro.webapi.SysStatusType;

@Component
public class AllegoVersionHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    private IAllegroClientFactory allegro;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        SysStatusType status = allegro.getAll().get(0).getStatus();

        builder.withDetail("apiVersion", status.getApiVersion());
        builder.withDetail("attribVersion", status.getAttribVersion());
        builder.withDetail("catsVersion", status.getCatsVersion());
        builder.withDetail("formSellVersion", status.getFormSellVersion());
        builder.withDetail("programVersion", status.getProgramVersion());
        builder.withDetail("siteVersion", status.getSiteVersion());
        builder.withDetail("verKey", status.getVerKey());

        builder.up();
    }

}
