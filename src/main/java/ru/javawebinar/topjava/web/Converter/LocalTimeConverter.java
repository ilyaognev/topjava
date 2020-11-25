package ru.javawebinar.topjava.web.Converter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;

public class LocalTimeConverter implements DateTimeConverter, Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        return DateTimeUtil.parseLocalTime(source);
    }
}
