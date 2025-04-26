package com.example.copsboot.report.web;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CreateReportRequestValidator.class})
public @interface ValidCreateReportRequest {
    String message() default "Invalid report";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
