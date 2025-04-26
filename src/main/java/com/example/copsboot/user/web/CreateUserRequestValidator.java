package com.example.copsboot.user.web;

import com.example.copsboot.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateUserRequestValidator implements ConstraintValidator<ValidCreateUserRequest, CreateUserRequest> {

    private final UserService userService;

    @Autowired
    public CreateUserRequestValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(CreateUserRequest request, ConstraintValidatorContext context) {
        if (userService.findUserByMobileToken(request.mobileToken()).isPresent()) {
            if (context != null) { // 👈 Agregado para que no truene en tests
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("There is already a user with the given mobile token.")
                        .addPropertyNode("mobileToken")
                        .addConstraintViolation();
            }
            return false;
        }
        return true;
    }
}
