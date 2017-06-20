package com.chifanhero.api.common.annotations;

import com.chifanhero.api.common.validators.UnionsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by shiyan on 4/29/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UnionsValidator.class)
@Documented
public @interface Unions {
    Union[] value();
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
