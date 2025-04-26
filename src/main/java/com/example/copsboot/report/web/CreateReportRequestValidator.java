package com.example.copsboot.report.web;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateReportRequestValidator implements ConstraintValidator<ValidCreateReportRequest, CreateReportRequest> {

    @Override
    public void initialize(ValidCreateReportRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateReportRequest value, ConstraintValidatorContext context) {
        if (value.trafficIncident() && value.numberOfInvolvedCars() <= 0) {
            return false;
        }
        return true;
    }
}
