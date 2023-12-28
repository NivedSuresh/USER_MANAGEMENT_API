package com.customer.module.CONTROLLERS;

import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Responses.AuthenticationResponse;
import com.module.library.SERVICES.AuthenticationService;
import com.module.library.SERVICES.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/signup")
public class SignupController {

    AuthenticationService authenticationService;
    JwtService jwtService;

    public SignupController(AuthenticationService authenticationService,  JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @InitBinder
    public void removeWhiteSpaces(WebDataBinder dataBinder){
        StringTrimmerEditor ste = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, ste);
    }

    @PostMapping()
    public AuthenticationResponse signup(@RequestBody @Valid SignUpRequest signUpRequest){

        this.authenticationService.SignupUser(signUpRequest);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signUpRequest.getEmail(), signUpRequest.getPassword(),
                List.of(new SimpleGrantedAuthority("CUSTOMER"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthenticationResponse(jwtService.generateJwt(authentication),
                "Signed Up successfully!",  "CUSTOMER");
    }

}
