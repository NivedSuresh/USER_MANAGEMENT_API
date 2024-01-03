package com.module.user.controllers;

import com.module.library.SERVICES.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserListController {

    UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/get_user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        try{
            System.out.println(id);
            return ResponseEntity.ok(userService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

}
