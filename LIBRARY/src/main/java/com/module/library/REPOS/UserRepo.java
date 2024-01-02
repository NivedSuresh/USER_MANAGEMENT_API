package com.module.library.REPOS;

import com.module.library.PAYLOAD.Responses.CustomerProjection;
import com.module.library.MODELS.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndRole(String email, String authority);

    CustomerProjection findByEmailAndRole(String email, String role);
}
