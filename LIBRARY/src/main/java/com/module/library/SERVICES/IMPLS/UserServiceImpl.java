package com.module.library.SERVICES.IMPLS;

import com.module.library.EXCEPTIONS.AccountAlreadyExistsException;
import com.module.library.EXCEPTIONS.DatabaseConnectionFailedException;
import com.module.library.EXCEPTIONS.InvalidStateException;
import com.module.library.MODELS.UserEntity;
import com.module.library.PAYLOAD.Requests.SignUpRequest;
import com.module.library.REPOS.UserRepo;
import com.module.library.SERVICES.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    UserRepo userRepo;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        try{ List<UserEntity> users =  userRepo.findAll();
            System.out.println(users); return users;}
        catch (Exception e){
            e.printStackTrace();
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try{ return userRepo.existsByEmail(email); }
        catch (Exception e){
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

    @Override
    public UserEntity findByEmail(String email) {
        try{ return userRepo.findByEmail(email); }
        catch (Exception e){
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

    @Override
    public UserEntity addUserToDB(SignUpRequest signUpRequest){
        try{
            if (userRepo.existsByEmail(signUpRequest.getEmail()) && signUpRequest.getId() == null)
                throw new AccountAlreadyExistsException("The email which you're trying to register already exists");
            if(!Objects.equals(signUpRequest.getPassword(), signUpRequest.getConfirmPassword()))
                throw new InvalidStateException("Passwords doesn't match");

            return userRepo.save(
                    new UserEntity(
                            signUpRequest.getId(),
                            signUpRequest.getEmail(),
                            signUpRequest.getUsername(),
                            false,
                            null,
                            passwordEncoder.encode(signUpRequest.getPassword()),
                            signUpRequest.getPhoneNumber(),
                            "CUSTOMER"
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof AccountAlreadyExistsException || e instanceof  InvalidStateException) throw e;
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

    @Override
    public UserEntity findById(String id) {
        try{
            Optional<UserEntity> user = userRepo.findById(id);
            UserEntity userEntity = null;
            if(user.isPresent()){
                userEntity = user.get();
                userEntity.setPassword(null);
                return userEntity;
            }
            else throw new UsernameNotFoundException("Couldn't find User");
        }catch (Exception e){
            if(e instanceof UsernameNotFoundException) throw e;
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

    @Override
    public void deleteById(String id) {
        try{
            userRepo.deleteById(id);
        }catch (Exception e){
            throw new DatabaseConnectionFailedException("Unable to initiate connection with the server, try after sometime.");
        }
    }

}
