package com.module.library.SERVICES;

import com.module.library.MODELS.UserEntity;
import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Responses.CustomerProjection;

import java.util.List;

public interface UserService {
    List<CustomerProjection> getAllUsers();
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    UserEntity addUserToDB(SignUpRequest signUpRequest);

    UserEntity findById(String id);

    void deleteById(String id);
}
