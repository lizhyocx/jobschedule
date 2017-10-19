package com.lizhy.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 参数校验工具类
 * Created by lizhiyang on 2017-06-22 16:53.
 */
public class ValidateUtil {
    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> ValidateResult validator(T object) {
        if (null == object) {
            return new ValidateResult(false,
                    new String[]{"The object to be validated must not be null."});
        }

        Set<ConstraintViolation<T>> violations = validator.validate(object);
        int errSize = violations.size();

        String[] errMsg = new String[errSize];
        boolean result = true;
        if (errSize > 0) {
            int i = 0;
            for (ConstraintViolation<T> violation : violations) {
                errMsg[i] = violation.getMessage();
                i++;
            }
            result = false;
        }

        return new ValidateResult(result, errMsg);
    }
}
