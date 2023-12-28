package com.module.library.PAYLOAD.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDto {
    public String id;
    public String username;
    public String email;
    public String role;
    public String phoneNumber;
}
