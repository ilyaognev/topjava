package ru.javawebinar.topjava.web.Converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class LocalDateConverter extends DateTimeConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
