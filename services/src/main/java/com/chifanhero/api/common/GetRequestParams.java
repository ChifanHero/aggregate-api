package com.chifanhero.api.common;

import com.chifanhero.api.common.annotations.ParamKey;
import com.google.common.collect.ImmutableMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by shiyan on 4/29/17.
 */
public class GetRequestParams {

    public Map<String, Object> getParams() {
        this.validate();
        ImmutableMap.Builder<String, Object> params = new ImmutableMap.Builder<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            String paramKey = getParamKey(field);
            Object paramValue = getParamValue(field);
            if (paramKey != null && paramValue != null) {
                params.put(paramKey, paramValue);
            }
        }
        return params.build();
    }

    private Object getParamValue(Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(this);
            field.setAccessible(false);
            return value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getParamKey(Field field) {
        ParamKey annotation = field.getAnnotation(ParamKey.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return field.getName();
        }
    }

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GetRequestParams>> violations = validator.validate(this);
        violations.forEach(violation -> {
            throw new IllegalArgumentException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }
}
