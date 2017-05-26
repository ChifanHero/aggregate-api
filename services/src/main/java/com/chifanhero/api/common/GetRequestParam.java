package com.chifanhero.api.common;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by shiyan on 5/3/17.
 */
public abstract class GetRequestParam<T> {

    public abstract T getParam();

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GetRequestParam>> violations = validator.validate(this);
        violations.forEach(violation -> {
            throw new IllegalArgumentException(violation.getPropertyPath() + " " + violation.getMessage());
        });
    }
}
