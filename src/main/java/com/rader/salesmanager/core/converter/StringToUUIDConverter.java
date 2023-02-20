package com.rader.salesmanager.core.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUUIDConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(MappingContext<String, UUID> context) {
        String source = context.getSource();
        if (source == null || source.isEmpty()) {
            return null;
        }
        return UUID.fromString(source);
    }
}

