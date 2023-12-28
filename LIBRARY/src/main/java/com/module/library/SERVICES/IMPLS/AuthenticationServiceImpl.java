package com.module.library.SERVICES.IMPLS;

import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.EXCEPTIONS.AccountAlreadyExistsException;
import com.module.library.MODELS.UserEntity;
import com.module.library.REPOS.UserRepo;
import com.module.library.PAYLOAD.Requests.LoginRequest;
import com.module.library.SERVICES.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepo customerRepo;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepo customerRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void SignupUser(SignUpRequest signUpRequest) {
        if(customerRepo.existsByEmail(signUpRequest.getEmail()))
            throw new AccountAlreadyExistsException("The email has already been registered");
        if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()))
            throw new BadCredentialsException("Password and Confirm password doesn't match");

        customerRepo.save(
            new UserEntity(signUpRequest.getEmail(), signUpRequest.getUsername(), false,
                "", passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhoneNumber(), "CUSTOMER")
        );

    }
    @Override
    public Authentication loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        try{
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }catch (Exception e){
            return null;
        }
    }
}
