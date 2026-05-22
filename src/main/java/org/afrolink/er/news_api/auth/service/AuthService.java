package org.afrolink.er.news_api.auth.service;

import lombok.RequiredArgsConstructor;

import org.afrolink.er.news_api.auth.dto.AuthResponse;
import org.afrolink.er.news_api.auth.dto.LoginRequest;
import org.afrolink.er.news_api.auth.dto.RegisterRequest;
import org.afrolink.er.news_api.common.response.ApiResponse;
import org.afrolink.er.news_api.security.JwtService;
import org.afrolink.er.news_api.user.entity.User;
import org.afrolink.er.news_api.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ApiResponse<?> register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Conflict")
                    .errors(List.of("Email already exists"))
                    .build();
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("User registered")
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponse<AuthResponse> login(
            LoginRequest request) {

        User user = userRepository.findByEmail(
                request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .object(response)
                .errors(null)
                .build();
    }
}