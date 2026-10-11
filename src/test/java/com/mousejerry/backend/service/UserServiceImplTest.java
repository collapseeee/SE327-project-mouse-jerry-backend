package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.OrderDao;
import com.mousejerry.backend.dao.UserDao;
import com.mousejerry.backend.dto.*;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.security.JwtUtils;
import com.mousejerry.backend.security.TokenBlacklistService;
import com.mousejerry.backend.util.LabMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private LabMapper labMapper;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegister_Success() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userDao.existsByUsername("newuser")).thenReturn(false);
        when(userDao.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registered = userService.register(request);

        assertNotNull(registered);
        assertEquals("newuser", registered.getUsername());
        assertEquals("new@example.com", registered.getEmail());
        assertEquals("hashedPassword", registered.getPasswordHash());
        assertEquals(Role.ROLE_CUSTOMER, registered.getRole());
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_PasswordsDoNotMatch() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .password("password123")
                .confirmPassword("wrongpassword")
                .build();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.register(request));
        assertEquals("Passwords do not match!", ex.getMessage());
        verify(userDao, never()).save(any());
    }

    @Test
    void testRegister_DuplicateUsername() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("existinguser")
                .email("unique@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userDao.existsByUsername("existinguser")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.register(request));
        assertEquals("Username or Email already taken!", ex.getMessage());
        verify(userDao, never()).save(any());
    }

    @Test
    void testRegister_DuplicateEmail() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("uniqueuser")
                .email("existing@example.com")
                .password("password123")
                .confirmPassword("password123")
                .build();

        when(userDao.existsByUsername("uniqueuser")).thenReturn(false);
        when(userDao.existsByEmail("existing@example.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.register(request));
        assertEquals("Username or Email already taken!", ex.getMessage());
        verify(userDao, never()).save(any());
    }

    @Test
    void testLogin_CustomerSuccess() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("customer1")
                .password("password123")
                .build();

        User user = User.builder()
                .id(10L)
                .username("customer1")
                .email("customer1@example.com")
                .passwordHash("hashed")
                .role(Role.ROLE_CUSTOMER)
                .build();

        when(userDao.findByUsername("customer1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashed")).thenReturn(true);
        when(jwtUtils.generateToken(user)).thenReturn("mocked-jwt-token");

        AuthResponseDTO response = userService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(Role.ROLE_CUSTOMER, response.getRole());
        assertEquals("/home-page", response.getRedirectUrl());
    }

    @Test
    void testLogin_AdminSuccess() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("admin1")
                .password("adminpass")
                .build();

        User admin = User.builder()
                .id(1L)
                .username("admin1")
                .email("admin@example.com")
                .passwordHash("hashedAdmin")
                .role(Role.ROLE_ADMIN)
                .build();

        when(userDao.findByUsername("admin1")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("adminpass", "hashedAdmin")).thenReturn(true);
        when(jwtUtils.generateToken(admin)).thenReturn("admin-jwt-token");

        AuthResponseDTO response = userService.login(request);

        assertNotNull(response);
        assertEquals("admin-jwt-token", response.getToken());
        assertEquals(Role.ROLE_ADMIN, response.getRole());
        assertEquals("/admin/back-game", response.getRedirectUrl());
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("notfound")
                .password("pass")
                .build();

        when(userDao.findByUsername("notfound")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> userService.login(request));
    }

    @Test
    void testLogin_WrongPassword() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("customer1")
                .password("wrongpass")
                .build();

        User user = User.builder()
                .username("customer1")
                .passwordHash("hashed")
                .build();

        when(userDao.findByUsername("customer1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "hashed")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> userService.login(request));
    }

    @Test
    void testGetUserProfile_Success() {
        User user = User.builder()
                .id(5L)
                .username("customer1")
                .email("customer1@example.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        Order order = Order.builder()
                .id(100L)
                .orderNumber("ORD-100")
                .build();

        UserProfileDTO profileDTO = UserProfileDTO.builder()
                .id(5L)
                .username("customer1")
                .email("customer1@example.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(100L)
                .orderNumber("ORD-100")
                .build();

        when(userDao.findByUsername("customer1")).thenReturn(Optional.of(user));
        when(orderDao.findByUserIdOrderByPurchaseDateDesc(5L)).thenReturn(List.of(order));
        when(labMapper.toUserProfileDTO(user)).thenReturn(profileDTO);
        when(labMapper.toOrderDTOList(List.of(order))).thenReturn(List.of(orderDTO));

        UserProfileDTO result = userService.getUserProfile("customer1");

        assertNotNull(result);
        assertEquals(1, result.getOrders().size());
        assertEquals("ORD-100", result.getOrders().get(0).getOrderNumber());
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        when(userDao.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                userService.getUserProfile("nonexistent"));
    }

    @Test
    void testLogout() {
        userService.logout("test-token");
        verify(tokenBlacklistService, times(1)).blacklistToken("test-token");

        userService.logout(null);
        verify(tokenBlacklistService, times(1)).blacklistToken("test-token");
    }
}
