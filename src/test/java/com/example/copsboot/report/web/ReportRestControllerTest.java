package com.example.copsboot.report.web;

import com.example.copsboot.report.*;
import com.example.copsboot.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        AuthServerId authServerId = new AuthServerId(UUID.fromString("eaa8b8a5-a264-48be-98de-d8b4ae2750ac"));
        User user = new User(userId, "wim@example.com", authServerId, "some-token");

        when(userService.findUserByAuthServerId(authServerId)).thenReturn(Optional.of(user));
        when(userService.getUserById(userId)).thenReturn(user);
        when(reportService.createReport(any(CreateReportParameters.class))).thenReturn(new Report(
                new ReportId(UUID.randomUUID()),
                userId,
                Instant.parse("2023-04-11T22:59:03.189+02:00"),
                "This is a test report description. The suspect was wearing a black hat."
        ));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "picture.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[]{1, 2, 3}
        );

        mockMvc.perform(multipart("/api/reports")
                        .file(imageFile)
                        .param("dateTime", "2023-04-11T22:59:03.189+02:00")
                        .param("description", "This is a test report description. The suspect was wearing a black hat.")
                        .param("trafficIncident", "false")
                        .param("numberOfInvolvedCars", "0")
                        .with(jwt().jwt(builder -> builder.subject(authServerId.value().toString())
                                .claim("email", "wim@example.com"))
                                .authorities(new SimpleGrantedAuthority("ROLE_OFFICER"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("reporter").value("wim@example.com"))
                .andExpect(jsonPath("dateTime").value("2023-04-11T20:59:03.189Z"))
                .andExpect(jsonPath("description").value("This is a test report description. The suspect was wearing a black hat."));
        }
}
