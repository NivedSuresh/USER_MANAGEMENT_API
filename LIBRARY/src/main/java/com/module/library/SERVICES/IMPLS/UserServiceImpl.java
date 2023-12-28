package com.module.library.SERVICES.IMPLS;

import com.module.library.EXCEPTIONS.DatabaseConnectionFailedException;
import com.module.library.MODELS.UserEntity;
import com.module.library.REPOS.UserRepo;
import com.module.library.SERVICES.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        try{ return userRepo.findAll(); }
        catch (Exception e){
            throw new DatabaseConnectionFailedException("Couldn't load users from Database");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try{ return userRepo.existsByEmail(email); }
        catch (Exception e){
            throw new DatabaseConnectionFailedException("Couldn't load users from Database");
        }
    }

    @Override
    public UserEntity findByEmail(String email) {
        try{ return userRepo.findByEmail(email); }
        catch (Exception e){
            throw new DatabaseConnectionFailedException("Couldn't load users from Database");
        }
    }
}
