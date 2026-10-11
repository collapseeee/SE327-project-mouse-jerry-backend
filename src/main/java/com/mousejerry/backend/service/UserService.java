package com.mousejerry.backend.service;

import com.mousejerry.backend.dto.AuthResponseDTO;
import com.mousejerry.backend.dto.LoginRequestDTO;
import com.mousejerry.backend.dto.RegisterRequestDTO;
import com.mousejerry.backend.dto.UserProfileDTO;
import com.mousejerry.backend.entity.User;

public interface UserService {

    User register(RegisterRequestDTO registerRequest);

    AuthResponseDTO login(LoginRequestDTO loginRequest);

    UserProfileDTO getUserProfile(String username);

    void logout(String token);
}
