package com.module.user.controllers;

import com.module.user.token_service.TokenService;
import com.module.library.PAYLOAD.Requests.LoginRequest;
import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Responses.AuthenticationResponse;
import com.module.library.SERVICES.AuthenticationService;
import com.module.library.SERVICES.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    AuthenticationService authenticationService;
    UserService userService;
    UserListController userListController;
    TokenService tokenService;

    public AuthController(AuthenticationService authenticationService, UserService userService,
                          UserListController userListController, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userListController = userListController;
        this.tokenService = tokenService;
    }

    @InitBinder
    public void removeWhiteSpaces(WebDataBinder dataBinder){
        StringTrimmerEditor ste = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, ste);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginHandler(@RequestBody() @Valid LoginRequest loginRequest){

        System.out.println(loginRequest);

        Authentication authentication = authenticationService.loginUser(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthenticationResponse(
                tokenService.generateToken(authentication), loginRequest.getEmail(),
                authentication.getAuthorities().stream().findFirst().get().getAuthority()
        );
    }

    @PutMapping("/add_user")
    public ResponseEntity<?> updateUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult){
        return addUser(signUpRequest, bindingResult);
    }

    @PostMapping("/add_user")
    public ResponseEntity<?> addUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()
            );
        }
        try{
            userService.addUserToDB(signUpRequest);
            return ResponseEntity.ok(userService.getAllUsers());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
