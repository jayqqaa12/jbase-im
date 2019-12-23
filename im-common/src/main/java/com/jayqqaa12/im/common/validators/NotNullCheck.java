package com.jayqqaa12.im.common.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullCheck implements ConstraintValidator<NotNull,Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o==null|| StringUtils.isEmpty(o))return false;
        return true;
    }
}
