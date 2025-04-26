package com.example.copsboot.report.web;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateReportRequestValidatorTest {

    private MockMultipartFile mockImage() {
        return new MockMultipartFile(
                "image",
                "dummy.png",
                "image/png",
                new byte[]{1} // 1 byte falso para pasar la validación
        );
    }

    @Test
    public void givenTrafficIncidentWithZeroInvolvedCars_invalid() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest request = new CreateReportRequest(
                    Instant.now(),
                    "The suspect ran away.",
                    true,
                    0,
                    mockImage() // 👈 nuevo argumento
            );

            Set violations = validator.validate(request);
            assertThat(violations).hasSize(1);
        }
    }

    @Test
    public void givenTrafficIncidentWithPositiveInvolvedCars_valid() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest request = new CreateReportRequest(
                    Instant.now(),
                    "The suspect ran away.",
                    true,
                    2,
                    mockImage()
            );

            Set violations = validator.validate(request);
            assertThat(violations).isEmpty();
        }
    }

    @Test
    public void givenNoTrafficIncident_involvedCarsDoesNotMatter() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest request = new CreateReportRequest(
                    Instant.now(),
                    "The suspect ran away.",
                    false,
                    0,
                    mockImage()
            );

            Set violations = validator.validate(request);
            assertThat(violations).isEmpty();
        }
    }
}
