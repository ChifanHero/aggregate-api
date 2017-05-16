package com.chifanhero.api.controllers;

import com.chifanhero.api.common.annotations.Cfg;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by shiyan on 5/16/17.
 */
@RestController
public class ConfigsController {

    @RequestMapping("/configs")
    public List<Map> configs() {
        Reflections reflections = new Reflections("com.chifanhero.api");

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Cfg.class);
        List<Map> info = new ArrayList<>();
        Map<String, Map> env = new HashMap<>();
        env.put("environment", System.getenv());
        info.add(env);
        annotated.forEach(subType -> {
            try {
                info.add(getConfigInfo(subType));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return info;
    }

    private Map<String, Object> getConfigInfo(Class<?> clazz) throws IllegalAccessException {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("className", clazz.getName());
        info.put("prefix", getPrefix(clazz));
        List<Map> values = new ArrayList<>();
        info.put("values", values);
        for (Field field : clazz.getDeclaredFields()) {
            Map<String, Object> value = new HashMap<>();
            if (java.lang.reflect.Modifier.isPublic(field.getModifiers()) && java.lang.reflect.Modifier.isStatic(field.getModifiers()) && java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                value.put(field.getName(), field.get(null));
            }
            values.add(value);
        }
        return info;
    }

    private String getPrefix(Class<?> clazz) {
        return clazz.getAnnotation(Cfg.class).prefix();
    }

}
