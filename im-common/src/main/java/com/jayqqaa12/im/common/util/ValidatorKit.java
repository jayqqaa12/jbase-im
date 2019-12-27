package com.jayqqaa12.im.common.util;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @author jinyong
 * @date 2019/9/30 16:08
 */
public class ValidatorKit {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static<T> void validate(@Valid T obj){
        Set<ConstraintViolation<@Valid T>> validateResult = validator.validate(obj, Default.class);
        if (!CollectionUtils.isEmpty(validateResult)) {
            String messages = validateResult.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((m1, m2) -> m1 + ";" + m2)
                    .orElse("参数格式不合法!");
            throw new IllegalArgumentException(messages);
        }
    }
}
