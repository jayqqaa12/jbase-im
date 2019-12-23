package com.jayqqaa12.im.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = NumberCheck.class)
public @interface Number {
    long min() default 0;
    long max() default Long.MAX_VALUE;

    String message() default "参数格式不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
