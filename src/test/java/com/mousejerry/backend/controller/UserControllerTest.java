package com.mousejerry.backend.controller;

import com.mousejerry.backend.config.GlobalExceptionHandler;
import com.mousejerry.backend.dto.OrderDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetCurrentUserProfile_Success() throws Exception {
        UserProfileDTO profileDTO = UserProfileDTO.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .role(Role.ROLE_CUSTOMER)
                .orders(List.of(
                        OrderDTO.builder().id(10L).orderNumber("ORD-10").build()
                ))
                .build();

        when(userService.getUserProfile("john")).thenReturn(profileDTO);

        mockMvc.perform(get("/api/v1/users/me").principal(() -> "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"))
                .andExpect(jsonPath("$.orders[0].orderNumber").value("ORD-10"));
    }

    @Test
    void testGetCurrentUserProfile_Unauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized());
    }
}
