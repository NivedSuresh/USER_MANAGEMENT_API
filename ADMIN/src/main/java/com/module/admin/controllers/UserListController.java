package com.module.admin.controllers;


import com.module.library.MODELS.UserEntity;
import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Responses.UserDto;
import com.module.library.SERVICES.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserListController {

    UserService userService;

    public UserListController(UserService userService) {
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
            return getAllUsers();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        try{
            userService.deleteById(id);
            return getAllUsers();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

}
