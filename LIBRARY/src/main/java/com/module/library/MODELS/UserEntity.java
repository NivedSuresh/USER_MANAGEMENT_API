package com.module.library.MODELS;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class UserEntity {
    public UserEntity(String email, String username, boolean isBanned, String profilePicture,
                      String password, String phoneNumber, String authority) {
        this.email = email;
        this.username = username;
        this.isBanned = isBanned;
        this.profilePicture = profilePicture;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authority = authority;
    }

    @Id
    private String id;
    private String email;
    private String username;
    private boolean isBanned;
    private String profilePicture;
    private String password;
    private String phoneNumber;
    private String authority;
}
