package com.module.library.MODELS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserEntity {
    public UserEntity(String email, String username, boolean isBanned, String picture,
                      String password, String phoneNumber, String role) {
        this.email = email;
        this.username = username;
        this.isBanned = isBanned;
        this.picture = picture;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Id
    private String id;

    private String email;

    private String username;
    private boolean isBanned;
    private String picture;

    private String password;
    private String phoneNumber;
    private String role;
}
