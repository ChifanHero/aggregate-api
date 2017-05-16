package com.chifanhero.api.configs;

import com.chifanhero.api.common.annotations.Cfg;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ConfigHelper {

    static String getProperty(String key, String defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? value : defaultValue;
    }

    static Integer getPropertyAsInt(String key, Integer defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? Integer.valueOf(value) : defaultValue;
    }

    static List<String> getPropertyAsList(String key, List<String> defaultValue) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? toList(value) : defaultValue;
    }

    static <T> List<T> getPropertyAsList(Class<T> type, String key, List<T> defaultValue, TypeConverter<T> converter) {
        Preconditions.checkNotNull(key);
        String value = System.getenv(key);
        return value != null? toList(type, value, converter) : defaultValue;
    }

    static String getPrefix(Class<?> clazz) {
        Cfg annotation = clazz.getAnnotation(Cfg.class);
        return annotation.prefix();
    }

    private static List<String> toList(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, "|");
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    private static <T> List<T> toList(Class<T> type, String value, TypeConverter<T> converter) {
        StringTokenizer tokenizer = new StringTokenizer(value, "|");
        List<T> converted = new ArrayList<T>();
        while (tokenizer.hasMoreTokens()) {
            converted.add(converter.convert(tokenizer.nextToken()));
        }
        return converted;
    }

    interface TypeConverter<T> {
        T convert(String value);
    }
}
