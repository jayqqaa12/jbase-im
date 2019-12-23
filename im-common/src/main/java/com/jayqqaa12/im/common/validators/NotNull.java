package com.jayqqaa12.im.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = NotNullCheck.class)
public @interface NotNull {
    String message() default "参数不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
