package com.chifanhero.api.configs.helper;

import com.chifanhero.api.configs.annotation.Cfg;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigHelper {

    public static String getProperty(String key, String defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? value : defaultValue;
    }

    public static Integer getPropertyAsInt(String key, Integer defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? Integer.valueOf(value) : defaultValue;
    }

    public static List<String> getPropertyAsList(String key, List<String> defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? toList(value) : defaultValue;
    }

    public static <T> List<T> getPropertyAsList(Class<T> type, String key, List<T> defaultValue, TypeConverter<T> converter) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? toList(type, value, converter) : defaultValue;
    }

    public static String getPrefix(Class<?> clazz) {
        Cfg annotation = clazz.getAnnotation(Cfg.class);
        return annotation.prefix();
    }

    private static List<String> toList(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split("\\|"));
    }

    private static <T> List<T> toList(Class<T> type, String value, TypeConverter<T> converter) {
        if (value == null) {
            return Collections.emptyList();
        }
        List<T> converted = new ArrayList<T>();
        String[] elements = value.split("\\|");
        for (String element : elements) {
            converted.add(converter.convert(element));
        }
        return converted;
    }

    public interface TypeConverter<T> {
        T convert(String value);
    }
}
