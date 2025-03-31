package com.example.FireFly_backend.services;


import com.example.FireFly_backend.models.dto.auth.AuthenticationRequest;
import com.example.FireFly_backend.models.dto.auth.AuthenticationResponse;
import com.example.FireFly_backend.models.dto.auth.RegisterRequest;
import com.example.FireFly_backend.models.entity.User;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String refreshToken) throws IOException;

    AuthenticationResponse me(
            String jwtToken
    );

    void resetPassword(String token, String newPassword);

    void confirmRegistration(String verificationToken);

    User forgotPassword(String email);
}
