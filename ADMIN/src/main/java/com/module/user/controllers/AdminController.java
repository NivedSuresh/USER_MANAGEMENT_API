package com.module.user.controllers;

import com.module.library.SERVICES.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public boolean isAdmin(Authentication authentication){
        System.out.println("Triggered admin : "+authentication);
        if(authentication == null) return false;
        try{
            return authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList().get(0).equals("SCOPE_ADMIN");
        }catch (Exception e){
            e.printStackTrace();
            return false; }
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<?> getAllUsers(){
        try{ return ResponseEntity.ok(userService.getAllUsers()); }
        catch(Exception e){
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
