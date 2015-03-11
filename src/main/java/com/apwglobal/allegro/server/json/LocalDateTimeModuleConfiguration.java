package com.apwglobal.allegro.server.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeModuleConfiguration {

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    @Bean
    public LocalDateTimeModule localDateTimeModule() {
        return new LocalDateTimeModule();
    }

    private class LocalDateTimeModule extends SimpleModule {
        public LocalDateTimeModule() {
            addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        }
    }

    public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM).format(date));
        }
    }

}
