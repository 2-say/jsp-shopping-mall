package com.nhnacademy.shoppingmall.common;

import com.nhnacademy.shoppingmall.entity.user.domain.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

public class ValidationTest {

    @Test
    void beanValidation() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        User user = new User(" ", "  ", " " ,  " " , User.Auth.ROLE_USER, 1000, LocalDateTime.now(), null);

        Set<ConstraintViolation<User>> validate = validator.validate(user);
        for (ConstraintViolation<User> violation : validate) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }

    }
}
