package com.jayqqaa12.im.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberCheck implements ConstraintValidator<Number,Object> {

    private long min;
    private long max;

    @Override
    public void initialize(Number integerValue) {
        this.min = integerValue.min();
        this.max = integerValue.max();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o==null)return false;
        try {
            Long param = Long.parseLong(o.toString());
            if(param<min||param>max){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
