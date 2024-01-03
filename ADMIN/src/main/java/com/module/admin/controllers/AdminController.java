package com.module.admin.controllers;

import com.module.library.SERVICES.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<?> getAllUsers(){
        try{
            return ResponseEntity.ok(userService.getAllUsers());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        try{
            System.out.println("reached controller");
            userService.deleteById(id);
            return getAllUsers();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
}
