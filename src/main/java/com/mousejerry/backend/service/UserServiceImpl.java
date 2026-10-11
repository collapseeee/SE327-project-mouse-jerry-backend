package com.mousejerry.backend.service;

import com.mousejerry.backend.dao.OrderDao;
import com.mousejerry.backend.dao.UserDao;
import com.mousejerry.backend.dto.AuthResponseDTO;
import com.mousejerry.backend.dto.LoginRequestDTO;
import com.mousejerry.backend.dto.RegisterRequestDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.Order;
import com.mousejerry.backend.entity.Role;
import com.mousejerry.backend.entity.User;
import com.mousejerry.backend.security.JwtUtils;
import com.mousejerry.backend.security.TokenBlacklistService;
import com.mousejerry.backend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final LabMapper labMapper;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    @Transactional
    public User register(RegisterRequestDTO registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        if (userDao.existsByUsername(registerRequest.getUsername()) || userDao.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Username or Email already taken!");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userDao.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        String token = jwtUtils.generateToken(user);
        String redirectUrl = (user.getRole() == Role.ROLE_ADMIN) ? "/admin/back-game" : "/home-page";

        return AuthResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .redirectUrl(redirectUrl)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(String username) {
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        List<Order> orders = orderDao.findByUserIdOrderByPurchaseDateDesc(user.getId());
        UserProfileDTO profile = labMapper.toUserProfileDTO(user);
        profile.setOrders(labMapper.toOrderDTOList(orders));
        return profile;
    }

    @Override
    public void logout(String token) {
        if (token != null && !token.isBlank()) {
            tokenBlacklistService.blacklistToken(token);
        }
        SecurityContextHolder.clearContext();
    }
}
