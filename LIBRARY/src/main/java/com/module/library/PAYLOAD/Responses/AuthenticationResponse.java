package com.module.library.PAYLOAD.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String JWT;
    private String email;
    private String role;
}
