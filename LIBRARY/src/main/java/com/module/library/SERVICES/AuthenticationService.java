package com.module.library.SERVICES;

import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Requests.LoginRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication loginUser(LoginRequest loginRequest);
}
