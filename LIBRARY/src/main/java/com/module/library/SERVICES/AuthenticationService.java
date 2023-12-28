package com.module.library.SERVICES;

import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Requests.LoginRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    void SignupUser(SignUpRequest customer);

    Authentication loginUser(LoginRequest loginRequest);
}
