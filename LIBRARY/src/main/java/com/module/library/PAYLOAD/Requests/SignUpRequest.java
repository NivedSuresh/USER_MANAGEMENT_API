package com.module.library.PAYLOAD.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SignUpRequest {

    String id;

    @Email
    @NotEmpty(message = "The entered email is not valid.")
    private String email;

    @NotEmpty(message = "Username cannot be empty.")
    private String username;

    @Size(message = "Password should be minimum 5 characters", min = 4)
    private String password;
    @Size(message = "Password should be minimum 5 characters", min = 4)
    private String confirmPassword;

    @Size(message = "Phone number should be exactly 10 digits.", min = 10, max = 10)
    private String phoneNumber;

    private String role;

    private String profilePicture;

}
