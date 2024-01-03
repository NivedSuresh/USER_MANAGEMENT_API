package com.module.library.SERVICES;

import com.module.library.MODELS.UserEntity;
import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.PAYLOAD.Responses.UserProjection;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserProjection> getAllUsers();
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
    void addUserToDB(SignUpRequest signUpRequest);

    UserEntity findById(String id);

    void deleteById(String id);

    UserProjection findUserProjectionWithEmail(Map<String, Object> claims);

    void saveUser(UserEntity user);
}
