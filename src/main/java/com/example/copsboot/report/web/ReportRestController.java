package com.example.copsboot.report.web;

import com.example.copsboot.report.*;
import com.example.copsboot.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService service;
    private final UserService userService;

    public ReportRestController(ReportService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createReport(@AuthenticationPrincipal Jwt jwt,
                                  @Valid CreateReportRequest request) {
        AuthServerId authServerId = new AuthServerId(UUID.fromString(jwt.getSubject()));
        User user = userService.findUserByAuthServerId(authServerId)
                .orElseThrow(() -> new UserNotFoundException(authServerId));

        Report report = service.createReport(request.toParameters(user.getId()));
        return ReportDto.fromReport(report, userService);
    }
}
