package com.example.copsboot.report.web;

import com.example.copsboot.report.*;
import com.example.copsboot.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportRestController.class)
@Import(com.example.copsboot.infrastructure.security.WebSecurityConfiguration.class)
public class ReportRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private UserService userService;

    @Test
    public void officerIsAbleToPostAReport() throws Exception {
        UserId userId = new UserId(UUID.randomUUID());
        AuthServerId authServerId = new AuthServerId(UUID.randomUUID());

        User user = new User(
                userId,
                "officer@example.com",
                authServerId,
                "some-mobile-token"
        );

        ReportId reportId = new ReportId(UUID.randomUUID());
        Report report = new Report(
                reportId,
                userId,
                Instant.parse("2024-04-24T18:30:00Z"),
                "The suspect was wearing a blue jacket."
        );

        when(userService.findUserByAuthServerId(authServerId)).thenReturn(Optional.of(user));
        when(userService.getUserById(userId)).thenReturn(user);
        when(reportService.createReport(any(CreateReportParameters.class))).thenReturn(report);

        mockMvc.perform(post("/api/reports")
                        .with(jwt().jwt(builder -> builder
                                .subject(authServerId.value().toString())
                                .claim("email", "officer@example.com"))
                                .authorities(new SimpleGrantedAuthority("ROLE_OFFICER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dateTime": "2024-04-24T18:30:00Z",
                                  "description": "The suspect was wearing a blue jacket."
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("reporter").value("officer@example.com"))
                .andExpect(jsonPath("dateTime").value("2024-04-24T18:30:00Z"))
                .andExpect(jsonPath("description").value("The suspect was wearing a blue jacket."));
    }
}
