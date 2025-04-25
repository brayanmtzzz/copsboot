package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.security.WebSecurityConfiguration;
import com.example.copsboot.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@Import(WebSecurityConfiguration.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void givenUnauthenticatedUser_userInfoEndpointReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenAuthenticatedUser_userInfoEndpointReturnsOk() throws Exception {
        String subject = UUID.randomUUID().toString();
        UserId userId = new UserId(UUID.randomUUID());

        when(userService.findUserByAuthServerId(new AuthServerId(UUID.fromString(subject))))
                .thenReturn(Optional.of(new User(
                        userId,
                        "test@example.com",
                        new AuthServerId(UUID.fromString(subject)),
                        "some-token"
                )));

        mockMvc.perform(get("/api/users/me").with(jwt().jwt(builder -> builder.subject(subject))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("subject").value(subject))
                .andExpect(jsonPath("claims").isMap())
                .andExpect(jsonPath("userId").value(userId.asString()));
    }

    @Test
    void givenAuthenticatedOfficer_userIsCreated() throws Exception {
        UserId userId = new UserId(UUID.randomUUID());
        when(userService.createUser(any(CreateUserParameters.class)))
                .thenReturn(new User(userId,
                        "wim@example.com",
                        new AuthServerId(UUID.fromString("eaa8b8a5-a264-48be-98de-d8b4ae2750ac")),
                        "mobile-token-demo"));

        mockMvc.perform(post("/api/users")
                        .with(jwt().jwt(builder -> builder.subject(UUID.randomUUID().toString()))
                                .authorities(new SimpleGrantedAuthority("ROLE_OFFICER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "mobileToken": "mobile-token-demo"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userId").value(userId.asString()))
                .andExpect(jsonPath("email").value("wim@example.com"))
                .andExpect(jsonPath("authServerId").value("eaa8b8a5-a264-48be-98de-d8b4ae2750ac"));
    }

    @Test
    void givenUserWithoutOfficerRole_forbiddenIsReturned() throws Exception {
        mockMvc.perform(post("/api/users")
                        .with(jwt().jwt(builder -> builder.subject(UUID.randomUUID().toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "mobileToken": "mobile-token-demo"
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}
