package com.module.library.AUTH;

import com.module.library.MODELS.UserEntity;
import com.module.library.REPOS.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByEmail(username);

        if(userEntity == null) throw new BadCredentialsException("Credentials doesn't exist");

        return new CustomUserDetails(
                userEntity.isBanned(),
                userEntity.getPassword(),
                userEntity.getUsername(),
                userEntity.getPhoneNumber(),
                userEntity.getEmail(),
                new ArrayList<>(List.of(new SimpleGrantedAuthority(userEntity.getRole())))
        );
    }
}
