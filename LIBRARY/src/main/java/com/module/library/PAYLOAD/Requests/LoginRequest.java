package com.module.library.PAYLOAD.Requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter
@ToString
public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty")
    String email;
    @NotEmpty(message = "password cannot be empty")
    String password;
    String otp;
}
