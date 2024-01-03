package com.module.library.REPOS;

import com.module.library.PAYLOAD.Responses.UserProjection;
import com.module.library.MODELS.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);

    List<UserProjection> findAllByRole(String role);

    UserProjection findByEmailAndRole(String email, String role);


}
