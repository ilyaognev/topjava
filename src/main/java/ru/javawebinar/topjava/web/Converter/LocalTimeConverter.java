package ru.javawebinar.topjava.web.Converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class LocalTimeConverter extends DateTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source);
    }
}
