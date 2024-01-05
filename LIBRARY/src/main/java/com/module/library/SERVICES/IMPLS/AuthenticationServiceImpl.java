package com.module.library.SERVICES.IMPLS;

import com.module.library.EXCEPTIONS.InvalidStateException;
import com.module.library.REPOS.UserRepo;
import com.module.library.PAYLOAD.Requests.LoginRequest;
import com.module.library.SERVICES.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Authentication loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        try{
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Authentication logoutUser() {
        try{
            SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                    "Anonymous Authentication",
                    null,
                    null
            ));
            return SecurityContextHolder.getContext().getAuthentication();
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidStateException("Unable to logout!");
        }
    }
}
