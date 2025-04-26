package com.example.copsboot.user.web;

import com.example.copsboot.user.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateUserRequestValidatorTest {

    private CreateUserRequestValidator validator;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        validator = new CreateUserRequestValidator(userService);
    }

    @Test
    public void invalidIfAlreadyUserWithGivenMobileToken() {
        String mobileToken = "abc123";

        when(userService.findUserByMobileToken(mobileToken))
                .thenReturn(Optional.of(new User(
                        new UserId(UUID.randomUUID()),
                        "wim@example.com",
                        new AuthServerId(UUID.randomUUID()),
                        mobileToken
                )));

        CreateUserRequest request = new CreateUserRequest(mobileToken);

        boolean isValid = validator.isValid(request, null);

        assertThat(isValid).isFalse();
    }

    @Test
    public void validIfNoUserWithGivenMobileToken() {
        String mobileToken = "abc123";

        when(userService.findUserByMobileToken(mobileToken)).thenReturn(Optional.empty());

        CreateUserRequest request = new CreateUserRequest(mobileToken);

        boolean isValid = validator.isValid(request, null);

        assertThat(isValid).isTrue();
    }
}
