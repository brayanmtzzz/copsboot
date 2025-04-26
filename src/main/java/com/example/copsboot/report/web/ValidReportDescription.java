package com.example.copsboot.report.web;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ReportDescriptionValidator.class })
public @interface ValidReportDescription {
    String message() default "The description must mention the word 'suspect'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
