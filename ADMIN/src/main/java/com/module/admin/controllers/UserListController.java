package com.module.admin.controllers;


import com.module.library.PAYLOAD.Responses.UserDto;
import com.module.library.SERVICES.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserListController {

    UserService userService;

    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
