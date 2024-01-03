package com.module.library.REPOS;

import com.module.library.PAYLOAD.Responses.CustomerProjection;
import com.module.library.MODELS.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndRole(String email, String authority);

    List<CustomerProjection> findAllByRole(String role);

    CustomerProjection findByEmailAndRole(String email, String role);
}
