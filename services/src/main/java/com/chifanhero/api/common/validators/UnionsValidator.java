package com.chifanhero.api.common.validators;

import com.chifanhero.api.common.annotations.Union;
import com.chifanhero.api.common.annotations.Unions;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by shiyan on 4/29/17.
 */
public class UnionsValidator implements ConstraintValidator<Unions, Object> {

    private Union[] unions;

    @Override
    public void initialize(Unions unions) {
        this.unions = unions.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        boolean isValid = true;
        for (Union union : unions) {
            try {
                String field1Value = BeanUtils.getProperty(o, union.field1());
                String field2Value = BeanUtils.getProperty(o, union.field2());
                String message = null;
                if (field1Value == null && field2Value == null) {
                    message = "m1"; // TODO - message
                    addConstraintViolation(constraintValidatorContext, message, union.field1(), union.field2());
                    isValid = false;
                } else if (field1Value != null && field2Value != null) {
                    message = "m2"; // TODO - message
                    addConstraintViolation(constraintValidatorContext, message, union.field1(), union.field2());
                    isValid = false;
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return isValid;
    }

    private void addConstraintViolation(ConstraintValidatorContext constraintValidatorContext, String message, String... nodes) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder
                = constraintValidatorContext.buildConstraintViolationWithTemplate(message);
        for (String node : nodes) {
            constraintViolationBuilder.addNode(node);
        }
        constraintViolationBuilder.addConstraintViolation();
    }
}
