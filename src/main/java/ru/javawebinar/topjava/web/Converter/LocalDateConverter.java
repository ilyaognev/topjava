package ru.javawebinar.topjava.web.Converter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;

public class LocalDateConverter implements DateTimeConverter, Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return DateTimeUtil.parseLocalDate(source);
    }
}
