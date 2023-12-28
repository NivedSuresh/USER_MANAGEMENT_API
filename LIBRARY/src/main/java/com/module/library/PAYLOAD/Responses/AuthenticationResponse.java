package com.module.library.PAYLOAD.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String jwtToken;
    private  String message;
    private String role;
}
