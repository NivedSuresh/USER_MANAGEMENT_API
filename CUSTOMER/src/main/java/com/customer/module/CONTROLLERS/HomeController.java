package com.customer.module.CONTROLLERS;

import com.module.library.PAYLOAD.Responses.CustomerProjection;
import com.module.library.REPOS.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    UserRepo userRepo;

    public HomeController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProjection> customerProjectionResponseEntity(Authentication userDetails){
        if(userDetails == null) throw new BadCredentialsException("Cannot access this data as user cannot be authenticated by the server");
        try{
            return new ResponseEntity<>(userRepo
                    .findByEmailAndAuthority(userDetails.getName(), "CUSTOMER"),
                    HttpStatus.ACCEPTED);
        }catch (Exception e){
            return null;
        }
    }

}
