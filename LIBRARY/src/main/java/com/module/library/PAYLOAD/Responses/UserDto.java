package com.module.library.PAYLOAD.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private String picture;
}
