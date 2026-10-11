package com.mousejerry.backend.config;

import com.mousejerry.backend.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthFilter;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        String raw = "mySecretPassword";
        String encoded = encoder.encode(raw);
        assertTrue(encoder.matches(raw, encoded));
    }

    @Test
    void testAuthenticationProvider() {
        AuthenticationProvider provider = securityConfig.authenticationProvider();
        assertNotNull(provider);
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();
        assertNotNull(source);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/auth/login");
        CorsConfiguration config = source.getCorsConfiguration(request);

        assertNotNull(config);
        assertTrue(config.getAllowedOrigins().contains("http://localhost:3000"));
        assertTrue(config.getAllowCredentials());
    }
}
