package com.module.library.SERVICES;

import com.module.library.MODELS.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
