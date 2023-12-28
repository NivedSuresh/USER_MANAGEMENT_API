package com.customer.module.CONTROLLERS;

import com.module.library.PAYLOAD.Requests.LoginRequest;
import com.module.library.PAYLOAD.Responses.AuthenticationResponse;
import com.module.library.SERVICES.AuthenticationService;
import com.module.library.SERVICES.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    JwtService jwtService;
    AuthenticationService authenticationService;


    public LoginController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping()
    public AuthenticationResponse loginHandler(@RequestBody() @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationService.loginUser(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponse(jwtService.generateJwt(authentication),
                        "Authentication Successful!", "CUSTOMER");
    }
}
