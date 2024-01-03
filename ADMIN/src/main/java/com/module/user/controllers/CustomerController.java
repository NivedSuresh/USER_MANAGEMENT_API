package com.module.user.controllers;

import com.module.library.SERVICES.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {

    UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get_principal")
    public ResponseEntity<?> getActiveUser(@AuthenticationPrincipal Jwt principal){
        try{
            return ResponseEntity.ok(userService.findUserProjectionWithEmail(principal.getClaims()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
