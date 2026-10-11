package com.mousejerry.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.AuthResponseDTO;
import com.mousejerry.backend.dto.LoginRequestDTO;
import com.mousejerry.backend.dto.RegisterRequestDTO;
import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testRegister_Success() throws Exception {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        User user = User.builder()
                .id(1L)
                .username("newuser")
                .email("new@example.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        when(userService.register(any(RegisterRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void testRegister_ValidationFailure() throws Exception {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("")
                .email("invalid-email")
                .password("123")
                .confirmPassword("123")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void testRegister_PasswordsMismatchException() throws Exception {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .password("password123")
                .confirmPassword("diffpassword")
                .build();

        when(userService.register(any(RegisterRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Passwords do not match!"));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Passwords do not match!"));
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("customer1")
                .password("password123")
                .build();

        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .token("jwt-token-xyz")
                .type("Bearer")
                .id(2L)
                .username("customer1")
                .email("customer1@example.com")
                .role(Role.ROLE_CUSTOMER)
                .redirectUrl("/home-page")
                .build();

        when(userService.login(any(LoginRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-xyz"))
                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"))
                .andExpect(jsonPath("$.redirectUrl").value("/home-page"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("wronguser")
                .password("wrongpass")
                .build();

        when(userService.login(any(LoginRequestDTO.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password."));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password."));
    }

    @Test
    void testLogout() throws Exception {
        doNothing().when(userService).logout(any());

        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer sample-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));

        verify(userService, times(1)).logout("sample-token");
    }
}
