package com.chifanhero.api.common.validators;

import com.chifanhero.api.common.annotations.Bonds;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by shiyan on 4/29/17.
 */
public class BondsValidator implements ConstraintValidator<Bonds, Object> {


    @Override
    public void initialize(Bonds bonds) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}