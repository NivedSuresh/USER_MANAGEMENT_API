package com.module.user.controllers;

import com.module.library.FILE_UTILS.FileUploadUtil;
import com.module.library.SERVICES.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
public class CustomerController {

    UserService userService;
    FileUploadUtil fileUploadUtil;

    public CustomerController(UserService userService, FileUploadUtil fileUploadUtil) {
        this.userService = userService;
        this.fileUploadUtil = fileUploadUtil;
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


    @PostMapping("/upload_image")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Jwt principal){
        try{
            if(file == null) {
                System.out.println("FILE is NULL");
                return null;
            }
            return ResponseEntity.ok(fileUploadUtil.uploadFile(file, principal.getClaims()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't upload file");
        }
    }

}
